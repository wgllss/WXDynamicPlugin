package com.wgllss.dynamic.sample.feature_startup.startup

import android.content.Context
import android.content.MutableContextWrapper
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wgllss.core.units.WLog
import com.wgllss.dynamic.sample.feature_home.fragment.HomeTabFragment
import kotlinx.coroutines.Deferred
import java.util.concurrent.ConcurrentHashMap

object HomeContains {
    private val map = ConcurrentHashMap<String, Deferred<View>>()
    private val mapFragment = ConcurrentHashMap<String, Deferred<Fragment>>()

    suspend fun getViewByKey(context: Context, key: String): View? {
        val view = map.remove(key)?.await()
        return if (view == null) {
            WLog.e(this, "getViewByKey key = $key is null")
            GenerateHomeLayout.getCreateViewByKey(context, key)
        } else {
            replaceContextForView(view, context)
            view?.takeIf {
                it.parent != null
            }?.let {
                (it.parent as ViewGroup).removeView(it)
            }
            view
        }
    }

    fun putViewByKey(key: String, deferred: Deferred<View>) {
        map[key] = deferred
    }

    fun putFragmentByKey(key: String, deferred: Deferred<Fragment>) {
        mapFragment[key] = deferred
    }

    suspend fun getFragmentByKey(key: String) = mapFragment.remove(key)?.await() ?: HomeTabFragment()


    private fun replaceContextForView(inflatedView: View?, context: Context?) {
        if (inflatedView == null || context == null) {
            return
        }
        val cxt = inflatedView.context
        if (cxt is MutableContextWrapper) {
            cxt.baseContext = context
        }
    }
}