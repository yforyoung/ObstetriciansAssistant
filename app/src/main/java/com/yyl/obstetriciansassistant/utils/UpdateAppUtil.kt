package com.yyl.obstetriciansassistant.utils

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Environment
import com.yyl.obstetriciansassistant.App.Companion.context
import java.io.File
import android.widget.Toast
import android.app.DownloadManager
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.net.Uri
import android.text.TextUtils
import com.google.gson.reflect.TypeToken
import com.yyl.obstetriciansassistant.*
import com.yyl.obstetriciansassistant.beans.ResponseData
import com.yyl.obstetriciansassistant.beans.Version
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException


class UpdateAppUtil private constructor() {
    lateinit var appVersionName: String
    private var currentVersionCode = 0
    private var ctx :Context=App.context
    @SuppressLint("StaticFieldLeak")
    private var reference: Long = 0
    private var downloadManager: DownloadManager? = null
    private lateinit var version: Version


    companion object {
        @SuppressLint("StaticFieldLeak")
        private val instance = UpdateAppUtil()
        fun getInstance(): UpdateAppUtil {
            return instance
        }

    }


    private fun getAPPVersionInfo() {
        val manager = ctx.packageManager
        try {
            val info = manager.getPackageInfo(ctx.packageName, 0)
            appVersionName = info.versionName // 版本名
            currentVersionCode = info.versionCode // 版本号
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }


    /**
     * 获取服务器上版本信息
     */

    private suspend fun getAPPServerVersion() {
        val json = HttpUtils.instance.doPostAsync("$REQUEST_URL/getnewversion", null)
        val re = SingleTon.instance.gson.fromJson<ResponseData<List<Version>>>(
            json,
            object : TypeToken<ResponseData<List<Version>>>() {}.type
        )
        version = re.data!![0]
    }

    /**
     * 判断版本号，更新APP
     */


    fun updateApp(loc: Int,context: Context) {
        GlobalScope.launch (UI){
            getAPPServerVersion()
            if (version.version.toInt() != currentVersionCode) {
                AlertDialog.Builder(context)
                    .setTitle("发现新版本")
                    .setMessage(version.content)
                    .setPositiveButton(
                        "下载并安装"
                    ) { _, _ -> downloadApp(version.url) }
                    .setNegativeButton("取消", null)
                    .create()
                    .show()
            } else {
                if (loc == 1)
                    toast("当前已是最新版本")
                val file = File(Environment.getExternalStorageDirectory().absolutePath, "/obstetricianAssistant/apk/")
                if (file.exists()) {
                    deleteDirWithFile(file)
                }
            }
        }

    }


    private fun deleteDirWithFile(dir: File?): Boolean {
        if (dir == null || !dir.exists() || !dir.isDirectory)
            return false
        for (file in dir.listFiles()) {
            if (file.isFile)
                file.delete() // 删除所有文件
            else if (file.isDirectory)
                deleteDirWithFile(file) // 递规的方式删除文件夹
        }
        return dir.delete()// 删除目录本身
    }

    private fun downloadApp(url: String) {
        if (TextUtils.isEmpty(url)) {
            return
        }

        try {
            val serviceString = Context.DOWNLOAD_SERVICE
            downloadManager = context.getSystemService(serviceString) as DownloadManager

            val uri = Uri.parse(url)
            val request = DownloadManager.Request(uri)
            request.allowScanningByMediaScanner()
            request.setVisibleInDownloadsUi(true)
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setMimeType("application/vnd.android.package-archive")

            val file =
                File(Environment.getExternalStorageDirectory().absolutePath + "obstetricianAssistant/apk/", "oa.apk")
            if (file.exists()) {
                file.delete()
            }
            request.setDestinationInExternalPublicDir("obstetricianAssistant/apk/", "oa.apk")
            reference = downloadManager!!.enqueue(request)
            //下载更新Apk 文件路径
            val downloadUpdateApkFilePath =
                Environment.getExternalStorageDirectory().absolutePath + "/obstetricianAssistant/apk/oa.apk"
            toast("开始下载更新")
            //注册广播接收者，监听下载状态
            ctx.registerReceiver(
                receiver,
                IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
            )
        } catch (exception: Exception) {
        }

    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            checkStatus()
        }
    }


    //检查下载状态
    private fun checkStatus() {
        val query = DownloadManager.Query()
        //通过下载的id查找
        query.setFilterById(reference)
        val c = downloadManager!!.query(query)
        if (c.moveToFirst()) {
            val status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS))
            when (status) {
                //下载暂停
                DownloadManager.STATUS_PAUSED -> {
                }
                //下载延迟
                DownloadManager.STATUS_PENDING -> {
                }
                //正在下载
                DownloadManager.STATUS_RUNNING -> {
                }
                //下载完成
                DownloadManager.STATUS_SUCCESSFUL ->
                    //下载完成安装APK
                    // installAPK();
                    toast("下载完成，请在通知栏中点击安装")
                //下载失败
                DownloadManager.STATUS_FAILED -> toast("下载失败")
            }
        }
        c.close()
    }

    init {
        getAPPVersionInfo()

    }


}