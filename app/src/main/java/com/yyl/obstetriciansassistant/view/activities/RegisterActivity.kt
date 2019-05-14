package com.yyl.obstetriciansassistant.view.activities

import Decoder.BASE64Encoder
import android.Manifest
import android.app.Activity
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yyl.obstetriciansassistant.*
import com.yyl.obstetriciansassistant.beans.ResponseData
import com.yyl.obstetriciansassistant.model.UserModel
import com.yyl.obstetriciansassistant.utils.HttpUtils
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.layout_progress_bar.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream


class RegisterActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private val userModel = UserModel()
    private var ne = false
    private var pe = false
    private var pce = false
    private var img = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initView()
        initListener()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> {
                if (resultCode == Activity.RESULT_OK) {
                    handleImageOnKitKat(data!!)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openAlbum()
        } else {
            toast("未授权")
        }
    }

    private fun handleImageOnKitKat(data: Intent) {
        var imagePath: String? = null
        val uri = data.data
        if (DocumentsContract.isDocumentUri(this, uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            assert(uri != null)
            if ("com.android.providers.media.documents" == uri!!.authority) {
                val id = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
                val selection = MediaStore.Images.Media._ID + "=" + id
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection)
            } else if ("com.android..providers.downloads.documents" == uri.authority) {
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads//public_downloads"),
                    java.lang.Long.valueOf(docId)
                )
                imagePath = getImagePath(contentUri, null)
            }
        } else {
            assert(uri != null)
            if ("content".equals(uri!!.scheme, ignoreCase = true)) {
                imagePath = getImagePath(uri, null)
            } else if ("file".equals(uri.scheme, ignoreCase = true)) {
                imagePath = uri.path
            }
        }                       //读取图片路径

        //设置头像
        val option = RequestOptions().optionalCircleCrop()
        Glide.with(this).load(imagePath).apply(option).into(register_head_pic)

        //保存图片到服务器
        img = getImageStr(imagePath!!)
    }

    private fun getImageStr(filePath: String): String {
        var inputStream: InputStream? = null
        var data: ByteArray? = null
        try {
            inputStream = FileInputStream(filePath)
            data = ByteArray(inputStream.available())
            inputStream.read(data)
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        // 加密
        val encoder = BASE64Encoder()
        return encoder.encode(data)
    }

    private fun getImagePath(uri: Uri, selection: String?): String? {
        var path: String? = null
        val cursor = contentResolver.query(uri, null, selection, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor.close()
        }
        return path
    }

    private fun openAlbum() {
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = "image/*"
        startActivityForResult(intent, 1)
    }

    private fun initView() {
        register_head_pic.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 2)
            } else {
                openAlbum()
            }

        }

        GlobalScope.launch(UI) {
            if (userModel.setRegisterBefore()) {
                val hospitalAdapter = ArrayAdapter<String>(
                    this@RegisterActivity,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    userModel.getHospitals()
                )

                val positionAdapter = ArrayAdapter<String>(
                    this@RegisterActivity,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    userModel.getPositions()
                )

                val clazzAdapter = ArrayAdapter<String>(
                    this@RegisterActivity,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    userModel.getClazz()
                )


                register_hospital_spinner.adapter = hospitalAdapter
                register_position_spinner.adapter = positionAdapter
                register_clazz_spinner.adapter = clazzAdapter

            }
        }

    }

    private fun initListener() {
        register_hospital_spinner.onItemSelectedListener = this
        register_position_spinner.onItemSelectedListener = this
        register_clazz_spinner.onItemSelectedListener = this

        register_pwd_confirm.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (register_pwd.text.toString() != s.toString()) {
                    register_pwd_confirm_layout.error = "两次密码输入不一致"
                    pce = false
                } else {
                    pce = true
                    register_pwd_confirm_layout.error = ""
                }
            }

        })
        register_pwd.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s == null || s.length < 6) {
                    register_pwd_layout.error = "密码小于6位"
                    pe = false
                } else {
                    pe = true
                    register_pwd_layout.error = ""

                }
            }

        })
        register_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s == null || s.length < 2) {
                    register_name_layout.error = "账号名不能小于2位"
                    ne = false
                } else {
                    ne = true
                    register_name_layout.error = ""
                }
            }

        })

        register_bt.setOnClickListener {
            val params = HashMap<String, String>()
            params["name"] = register_name.text.toString()
            params["password"] = register_pwd.text.toString()
            params["hospitalid"] = userModel.getHospitalId(register_hospital_spinner.selectedItemPosition)
            params["positionid"] = userModel.getPositionId(register_position_spinner.selectedItemPosition)
            params["departmentid"] = userModel.getClazzId(register_clazz_spinner.selectedItemPosition)
            params["imgstr"] = img
            params["imgname"] = ""

            if (ne && pe && pce) {
                progress_bar.visibility = View.VISIBLE

                HttpUtils.instance.doPost("$REQUEST_URL/register", params, object : HttpUtils.HttpCallBack {
                    override fun success(json: String) {
                        GlobalScope.launch(UI) {
                            val responseData = SingleTon.instance.gson.fromJson(json, ResponseData::class.java)
                            if (responseData.retcode == 1) {
                                toast("注册成功")
                                jump2Activity(this@RegisterActivity, LoadActivity::class.java)
                            } else {
                                toast("注册失败！${responseData.retmsg}")
                            }
                            progress_bar.visibility = View.GONE

                        }
                    }

                })
            }

        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }
}
