package com.wgllss.core.units
//
//import android.app.ActivityManager
//import android.content.Context
//import android.content.Intent
//import android.os.Build
//import androidx.annotation.RequiresApi
//
//object ServiceUtil {
//
//    /**
//     * 判断某个服务是否存在
//     *
//     * @param context
//     * @param className
//     * @return
//     */
//    fun isServiceExisted(context: Context, className: String): Boolean {
//        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
//        val serviceList = activityManager.getRunningServices(Int.MAX_VALUE)
//        if (serviceList == null || serviceList.size <= 0) {
//            return false
//        }
//        for (i in serviceList.indices) {
//            val serviceInfo = serviceList[i]
//            val serviceName = serviceInfo.service
//            if (serviceName.className == className) {
//                return true
//            }
//        }
//        return false
//    }
//
//    fun startService(packageContext: Context, cls: Class<*>) {
//        try {
//            if (!isServiceExisted(packageContext, cls.name)) {
//                val intent = Intent(packageContext, cls)
//                packageContext.startService(intent)
//            }
//        } catch (e: Exception) {
//        }
//    }
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    fun startForegroundService(packageContext: Context, cls: Class<*>) {
//        try {
//            if (!isServiceExisted(packageContext, cls.name)) {
//                val intent = Intent(packageContext, cls)
//                packageContext.startForegroundService(intent)
//            }
//        } catch (e: Exception) {
//
//        }
//    }
//
//    fun stopService(packageContext: Context, cls: Class<*>) {
//        try {
//            if (!isServiceExisted(packageContext, cls.name)) {
//                val intent = Intent(packageContext, cls)
//                packageContext.stopService(intent)
//            }
//        } catch (e: Exception) {
//        }
//    }
//}