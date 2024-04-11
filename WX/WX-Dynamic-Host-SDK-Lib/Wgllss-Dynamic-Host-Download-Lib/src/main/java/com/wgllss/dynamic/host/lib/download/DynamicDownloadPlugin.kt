package com.wgllss.dynamic.host.lib.download

import android.content.Context
import com.wgllss.dynamic.host.lib.protobuf.PluginMode
import okio.buffer
import okio.sink
import okio.source
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.*

class DynamicDownloadPlugin(private val face: IDynamicDownLoadFace) {
    companion object {
        private var mSSLSocketFactory: SSLSocketFactory? = null

        /**
         * 信任所有host
         */
        private val hnv = HostnameVerifier { _, _ -> true }

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

        private fun getHttpURLConnection(url: URL, connectTimeOut: Int): HttpURLConnection? {
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

    fun initDynamicByKey(context: Context, key: String, downloadDir: String, downLoadFileName: String) = initDynamicPlugin(context, face.getMapDLU()[key]!!, downloadDir, downLoadFileName)

    fun initDynamicPlugin(context: Context, downUrl: String, downloadDir: String, downLoadFileName: String): DownLoadResult {
        face?.run {
            val sb = StringBuilder(context.filesDir.absolutePath)
                .append(File.separator)
                .append(downloadDir)
                .append(File.separator)
                .append(downLoadFileName)
            val file = File(sb.toString())
            if (file.exists()) {
                if (isDebug())
                    android.util.Log.e("DynamicDownloadPlugin", "本地已经存在 $downLoadFileName")
                return DownLoadResult(context, downLoadFileName, file.absolutePath)
            } else {
                val fileDir = File(file.parent)
                if (!fileDir.exists()) {
                    fileDir.mkdirs()
                }
                val startTime = if (isDebug()) System.currentTimeMillis() else 0
                if (context != null && downUrl != null && downUrl.isNotEmpty()) {
                    var conn: HttpURLConnection? = null
                    try {
                        val urlS = if (downUrl.contains("http")) downUrl else "${getBaseL()}$downUrl"
                        val url = URL(urlS)
                        conn = getHttpURLConnection(url, 5000)!!
                        val inputStream = conn.inputStream
                        if (inputStream != null) {
                            val sinkBuffer = file.sink().buffer()
                            val bufferedSource = inputStream.source().buffer()
                            sinkBuffer.write(bufferedSource.readByteArray())
                            sinkBuffer.close()
                            bufferedSource.close()
                            inputStream.close()
                        }
                        if (isDebug()) {
                            val endTime = System.currentTimeMillis()
                            val times: Long = endTime - startTime
                            android.util.Log.e("DynamicDownloadPlugin", "$downLoadFileName 下载成功,耗时:$times ms ")
                        }
                        return DownLoadResult(context, downLoadFileName, file.absolutePath)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    } finally {
                        conn?.disconnect()
                    }
                }
            }
        }
        return DownLoadResult(context, " ", " ")
    }

    fun getPluginInfo(): PluginMode._PluginMode {
        var buf: ByteArray? = null
        face?.run {
            val startTime = if (isDebug()) System.currentTimeMillis() else 0
            var httpConnection: HttpURLConnection? = null
            try {
                if (isDebug()) {
                    android.util.Log.e("DynamicDownloadPlugin", "${getOtherDLU()}")
                }
                val url = URL(getOtherDLU())
                httpConnection = getHttpURLConnection(url, 5000)!!
                setConHead(httpConnection)
                httpConnection.connect()
                val responseCode = httpConnection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_PARTIAL) {
                    val instream = httpConnection.inputStream
                    if (instream != null) {
                        val bufferedSource = instream.source().buffer()
                        buf = bufferedSource.readByteArray()
                        bufferedSource.close()
                        instream.close()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                httpConnection?.disconnect() // 关闭连接
            }
            if (isDebug()) {
                val endTime = System.currentTimeMillis()
                val times: Long = endTime - startTime
                android.util.Log.e("DynamicDownloadPlugin", "请求网络耗时:$times ms ")
            }
        }
        val startTime = if (face.isDebug()) System.currentTimeMillis() else 0
        var pm: PluginMode._PluginMode? = null
        try {
            buf?.let {
                pm = PluginMode._PluginMode.parseFrom(buf);
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (face.isDebug()) {
            val endTime = System.currentTimeMillis()
            val times: Long = endTime - startTime
            android.util.Log.e("DynamicDownloadPlugin", "protobuf 序列号耗时:$times ms ")
        }
        return pm!!
    }
}