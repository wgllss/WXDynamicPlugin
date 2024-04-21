package com.wgllss.sample.datasource.net

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class HeaderInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val authorised = request
            .newBuilder()
            .addHeader("Connection", "keep-alive") //
            .addHeader("Accept-Language", "zh-CN,zh;q=0.9") //
            .addHeader("Upgrade-insecure-Requests", "1") //
        if (request.header("User-Agent") == null) {
            authorised.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36") //
        }
        return chain.proceed(authorised.build())
    }
}