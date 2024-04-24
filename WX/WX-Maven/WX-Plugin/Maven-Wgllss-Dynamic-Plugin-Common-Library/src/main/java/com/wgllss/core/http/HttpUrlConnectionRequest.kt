package com.wgllss.core.http

import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

object HttpUrlConnectionRequest {

    fun getServerJson(url: String): String {
        val result = StringBuilder()
        var httpConnection: HttpURLConnection? = null
        try {
            val url = URL(url)
            httpConnection = HttpRequest.getHttpURLConnection(url, 5000)
            HttpRequest.setConHead(httpConnection!!)
            httpConnection!!.connect()
            val responseCode = httpConnection!!.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_PARTIAL) {
                val instream = httpConnection!!.inputStream
                if (instream != null) {
                    val inputreader = InputStreamReader(instream)
                    val buffreader = BufferedReader(inputreader)
                    var line: String?
                    // 分行读取
                    while (buffreader.readLine().also { line = it } != null) {
                        result.append(line)
                    }
                    inputreader.close()
                    instream.close()
                    buffreader.close()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            httpConnection?.disconnect() // 关闭连接
        }
        return result.toString()
    }
}