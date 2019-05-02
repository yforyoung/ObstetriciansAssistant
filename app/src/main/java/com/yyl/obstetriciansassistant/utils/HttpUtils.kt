package com.yyl.obstetriciansassistant.utils

import com.yyl.obstetriciansassistant.UI
import com.yyl.obstetriciansassistant.log
import com.yyl.obstetriciansassistant.toast
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException

class HttpUtils private constructor() {

    companion object {
        val instance: HttpUtils = HttpUtils()
    }


    private val client: OkHttpClient = OkHttpClient()

    fun doPost(url: String, param: HashMap<String, String>?, callback: HttpCallBack) {
        val request: Request
        val requestBuilder = Request.Builder()
        val builder = FormBody.Builder()
        if (param != null) {
            for (map in param.entries) {
                val key = map.key
                val value: String = map.value
                builder.add(key, value)
            }
        }
        val requestBody = builder.build()

        request = requestBuilder
            .post(requestBody)
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                /*GlobalScope.launch(UI) {
                    callback.failed()
                }*/
                GlobalScope.launch(UI) {
                    toast(e.toString())
                }
                log(e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                /* GlobalScope.launch(UI) {*/
                val json = response.body()!!.string()
                log(json)
                callback.success(json)
                /* }*/
            }

        })
    }

    suspend fun doPostAsync(url: String, param: HashMap<String, String>?): String {
        val request: Request
        val requestBuilder = Request.Builder()
        val builder = FormBody.Builder()
        if (param != null) {
            for (map in param.entries) {
                val key = map.key
                val value: String = map.value
                builder.add(key, value)
            }
        }
        val requestBody = builder.build()

        request = requestBuilder
            .post(requestBody)
            .url(url)
            .build()

        try {

            val job = GlobalScope.async {
                client.newCall(request).execute()
            }
            val response = job.await()
            if (response.isSuccessful) {
                val json = response.body()!!.string()
                log(json)
                return json
            } else {
                log(response.message())
            }
        } catch (e: IOException) {
            toast(e.toString())
        }
        return "{\"retcode\":0,\"retmsg\":\"${response.message()}\",data:\"\"}"
    }

    interface HttpCallBack {
        fun success(json: String)
/*
        fun failed()
*/
    }


}