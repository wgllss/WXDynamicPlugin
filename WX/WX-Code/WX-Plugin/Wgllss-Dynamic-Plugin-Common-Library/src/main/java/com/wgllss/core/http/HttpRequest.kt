package com.wgllss.core.http

import java.net.HttpURLConnection
import java.net.URL
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.*

object HttpRequest {

    fun getHttpURLConnection(url: URL, connectTimeOut: Int): HttpURLConnection? {
        return try {
            if ("https" == url.protocol) {
                trustAllHosts()
                (url.openConnection() as HttpsURLConnection).apply {
                    HttpsURLConnection.setDefaultHostnameVerifier(hnv)
                    hostnameVerifier = hnv
                    HttpsURLConnection.setDefaultSSLSocketFactory(mSSLSocketFactory)
                    sslSocketFactory = mSSLSocketFactory
                    connectTimeout = 3 * connectTimeOut
                    readTimeout = 3 * connectTimeOut
                }
            } else {
                (url.openConnection() as HttpURLConnection).apply {
                    connectTimeout = connectTimeOut
                    readTimeout = connectTimeOut
                }
            }
        } catch (e: Exception) {
            null
        }
    }

    /**
     * 信任所有host
     */
    var hnv = HostnameVerifier { hostname, session -> true }

    /**
     * 设置https
     *
     * @author :Atar
     * @createTime:2015-9-17下午4:57:39
     * @version:1.0.0
     * @modifyTime:
     * @modifyAuthor:
     * @description:
     */
    private fun trustAllHosts() {
        try {
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }

                @Throws(CertificateException::class)
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                }
            })
            val sc = SSLContext.getInstance("TLS")
            sc.init(null, trustAllCerts, SecureRandom())
            if (mSSLSocketFactory == null) {
                mSSLSocketFactory = sc.socketFactory
            }
            HttpsURLConnection.setDefaultHostnameVerifier(hnv)
            HttpsURLConnection.setDefaultSSLSocketFactory(mSSLSocketFactory)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    var mSSLSocketFactory: SSLSocketFactory? = null

    /**
     * 设置请求头
     *
     * @param httpConnection
     */
    fun setConHead(httpConnection: HttpURLConnection) {
        httpConnection.apply {
            setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
            setRequestProperty("Upgrade-Insecure-Requests", "1")
            setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Mobile Safari/537.36")
            setRequestProperty("Accept-Language", "en-us,en;q=0.7,zh-cn;q=0.3")
//            httpConnection.setRequestProperty("Accept-Encoding", "gzip, deflate, br")
            setRequestProperty("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7")
            setRequestProperty("Keep-Alive", "300")
            setRequestProperty("Connection", "keep-alive")
            setRequestProperty("Cache-Control", "max-age=0")
        }
    }
}