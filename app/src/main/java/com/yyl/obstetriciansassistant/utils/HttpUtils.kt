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


        val job = GlobalScope.async {
            try {
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val json = response.body()!!.string()
                    log(json)
                    return@async json
                } else {
                    return@async "{\"retcode\":0,\"retmsg\":\"请求超时,请检查网络状态\",data:null}"
                }
            }catch (e:IOException){
                return@async "{\"retcode\":0,\"retmsg\":\"请求超时，请检查网络状态\",data:null}"
            }
        }
        return job.await().toString()


        /*   val response = job.await()
           if (response.isSuccessful) {
               val json = response.body()!!.string()
               log(json)
               return json
           } else {
               log(response.message())
           }
       } catch (e: IOException) {
           launch (UI){
               toast(e.toString())
           }
       }
       return "{\"retcode\":0,\"retmsg\":\"网络请求失败\",data:\"\"}"*/
    }

    interface HttpCallBack {
        fun success(json: String)
    }


}