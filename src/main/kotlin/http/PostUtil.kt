package cc.gxstudio.gmanager.http

import cc.gxstudio.gmanager.globalvar.httpClient
import okhttp3.FormBody
import okhttp3.Request

class PostUtil {
    //  var Client: OkHttpClient = OkHttpClient()
    private val requestBody = FormBody.Builder()
    private val request = Request.Builder()
    private var url = ""
    fun build(func: PostUtil.() -> Unit): String {
        func()
        return httpClient.newCall(
            request
                .url(url)
                .post(requestBody.build())
                .build())
            .execute().body!!.string()
    }
    
    fun url(urlString: String) {
        if (url == "") url = urlString
    }
    
    fun add(name: String, value: String) {
        requestBody.add(name, value)
    }
}