package com.wgllss.core.activity

import android.app.Activity
import android.os.Process
import com.wgllss.core.ex.finishActivity
import java.lang.reflect.Field
import java.lang.reflect.Method

object WActivityManager {

    fun getActivitys(block: (it: Activity) -> Any) {
        try {
            val activityThread = Class.forName("android.app.ActivityThread")
            val currentActivityThread: Method = activityThread.getDeclaredMethod("currentActivityThread")
            currentActivityThread.isAccessible = true
            //获取主线程对象
            val activityThreadObject: Any = currentActivityThread.invoke(null)
            val mActivitiesField: Field = activityThread.getDeclaredField("mActivities")
            mActivitiesField.isAccessible = true
            val mActivities = mActivitiesField[activityThreadObject] as Map<Any, Any>
            mActivities.forEach {
                val activityClientRecordClass: Class<*> = it.value.javaClass
                val activityField = activityClientRecordClass.getDeclaredField("activity")
                activityField.isAccessible = true
                activityField[it.value]?.let {
                    block.invoke(it as Activity)
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun popAllActivity() {
        getActivitys() {
            it.finishActivity()
        }
    }


    fun exitApplication() {
        object : Thread() {
            override fun run() {
                super.run()
                popAllActivity()
                Process.killProcess(Process.myPid())
                System.exit(0) // 常规java、c#的标准退出法，返回值为0代表正常退出
            }
        }.start()
    }
}