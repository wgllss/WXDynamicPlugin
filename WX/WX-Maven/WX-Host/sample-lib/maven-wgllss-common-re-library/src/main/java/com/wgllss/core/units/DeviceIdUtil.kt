package com.wgllss.core.units

import android.content.Context
import android.os.Build
import android.provider.Settings
import java.security.MessageDigest
import java.util.*

object DeviceIdUtil {

    private const val isDeviceSelfSerial = false

    //自己设备测试序号
    private val setsD = setOf("B05F9543937A5BA61901FC14F2540C62DA3E86C2", "2F6039397BA7EEC402E7036339963B23810CCBFD")

    //其他设备测试序号 命名可自定义
    private const val ELSED = "dxde_m_p"

    fun getDeviceId(): String {
        if (isDeviceSelfSerial) {
            //todo 模拟序列号
            return "2F6039397BA7EEC402E7036339963B23810CCBFD"
        } else {
            val context = AppGlobals.sApplication
            val sbDeviceId = StringBuilder()
            val androidID = getAndroidId(context)
            val id = getDeviceUUID(context).replace("-", "")
            if (androidID != null && androidID.isNotEmpty()) {
                sbDeviceId.append(androidID)
                sbDeviceId.append("|")
            }
            if (id != null && id.isNotEmpty()) {
                sbDeviceId.append(id)
            }
            if (sbDeviceId.toString().isNotEmpty()) {
                try {
                    val hash = getHashByString(sbDeviceId.toString())
                    val sha1 = bytesToHex(hash!!)
                    if (sha1 != null && sha1.isNotEmpty()) {
                        LogTimer.LogE(this, "sha1:$sha1")
                        return if (setsD.contains(sha1)) sha1 else ELSED
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
            val s = sbDeviceId.toString()
            return if (setsD.contains(s)) s else ELSED
        }
    }

    private fun bytesToHex(data: ByteArray): String? {
        val sb = StringBuilder()
        var stmp: String
        for (n in data.indices) {
            stmp = Integer.toHexString(data[n].toInt() and 0xFF)
            if (stmp.length == 1) sb.append("0")
            sb.append(stmp)
        }
        return sb.toString().uppercase(Locale.CHINA)
    }

    private fun getHashByString(data: String): ByteArray? {
        return try {
            val messageDigest: MessageDigest = MessageDigest.getInstance("SHA1")
            messageDigest.reset()
            messageDigest.update(data.toByteArray(charset("UTF-8")))
            messageDigest.digest()
        } catch (e: java.lang.Exception) {
            " ".toByteArray()
        }
    }

    private fun getDeviceUUID(context: Context): String {
        val sb = StringBuilder().apply {
            append("100001")
            append(Build.BOARD)
            append(Build.BRAND)
            append(Build.DEVICE)
            append(Build.HARDWARE)
            append(Build.ID)
            append(Build.MODEL)
            append(Build.PRODUCT)
        }
        val long = sb.toString().hashCode().toLong()
        return UUID(long, long).toString()
    }

    private fun getAndroidId(context: Context): String {
        try {
            return Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            )
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
        return " "
    }
}