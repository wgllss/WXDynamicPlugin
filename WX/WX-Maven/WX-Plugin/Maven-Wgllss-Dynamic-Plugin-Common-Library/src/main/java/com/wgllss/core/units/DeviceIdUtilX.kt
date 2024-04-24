package com.wgllss.core.units

import android.content.Context
import android.os.Build
import android.provider.Settings
import java.security.MessageDigest
import java.util.*

/**
 * 1847470F0A12C2F310F7311FA719DBFE50510192
 * 1847470F0A12C2F310F7311FA719DBFE50510192
 *
 * 2F6039397BA7EEC402E7036339963B23810CCBFD
 */
object DeviceIdUtilX {
    private const val isDeviceSelfSerial = false
    private const val WD = "B03E4AFB9ADC842C9B3BDB6C57346ACEF6CE7504"
    private const val CD = "2F6039397BA7EEC402E7036339963B23810CCBFD"
    private const val ELSED = "dxde_m_p"

    fun getDeviceId(context: Context, needDevice: Boolean = false): String {
        if (isDeviceSelfSerial) {
            //todo 模拟序列号
            return "2F6039397BA7EEC402E7036339963B23810CCBFD"
//            return getSerial(AppGlobals.sApplication)
        } else {
//            val context = AppGlobals.sApplication
            val sbDeviceId = StringBuilder()
//            val imei = getIMEI(context)
            val androidID = getAndroidId(context)
//            val serial = getSerial(context)
            val id = getDeviceUUID(context).replace("-", "")
            //追加imei
//            if (imei != null && imei.isNotEmpty()) {
//                sbDeviceId.append(imei)
//                sbDeviceId.append("|")
//            }
            //追加androidid
            if (androidID != null && androidID.isNotEmpty()) {
                sbDeviceId.append(androidID)
                sbDeviceId.append("|")
            }
            //追加serial
//            if (serial != null && serial.isNotEmpty()) {
//                sbDeviceId.append(serial)
//                sbDeviceId.append("|")
//            }
            //追加硬件uuid
            if (id != null && id.isNotEmpty()) {
                sbDeviceId.append(id)
            }
            if (sbDeviceId.toString().isNotEmpty()) {
                try {
                    val hash = getHashByString(sbDeviceId.toString())
                    val sha1 = bytesToHex(hash!!)
//                    LogTimer.LogE(this, sha1!!)
                    if (sha1 != null && sha1.isNotEmpty()) {
                        return if (needDevice || CD == sha1 || WD == sha1)
                            sha1 else ELSED
//                        return sha1
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
            val s = sbDeviceId.toString()
            return if (CD == s || WD == s)
                s else ELSED
        }
    }

    /**
     * 转16进制字符串
     *
     * @param data 数据
     * @return 16进制字符串
     */
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

    /**
     * 取SHA1
     *
     * @param data 数据
     * @return 对应的hash值
     */
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

    // //获得硬件uuid（根据硬件相关属性，生成uuid）（无需权限）  数字  0   -10
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
//            append(Build.SERIAL)
        }
        val long = sb.toString().hashCode().toLong()
        return UUID(long, long).toString()
    }

//    private fun getSerial(context: Context): String {
//        try {
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
//                return Build.SERIAL
//            } else {
//                val permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
//                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
//                    return Build.getSerial()
//                }
//            }
//        } catch (ex: Exception) {
//            ex.printStackTrace()
//        }
//        return " "
//    }

    /**
     * 获得设备的AndroidId
     *
     * @param context 上下文
     * @return 设备的AndroidId
     */
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
//
//    //需要获得READ_PHONE_STATE权限，>=6.0，默认返回null
//    private fun getIMEI(context: Context): String {
//        try {
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//                val permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
//                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
//                    val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
//                    return tm.deviceId ?: " "
//                }
//            }
//        } catch (ex: Exception) {
//            ex.printStackTrace()
//        }
//        return " "
//    }
}