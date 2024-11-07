package com.wgllss.sample.features_ui.page.other2.viewmodel

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wgllss.core.units.ImageUtils
import com.wgllss.core.units.ResourceUtils
import com.wgllss.core.units.WLog
import com.wgllss.core.viewmodel.BaseViewModel
import com.wgllss.core.widget.CommonToast
import com.wgllss.dynamic.host.lib.download.DynamicDownloadPlugin
import com.wgllss.dynamic.host.lib.impl.WXDynamicLoader
import com.wgllss.nativex.MainActivity
import com.wgllss.sample.features_ui.page.base.SkinContains
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ComposeDemoViewModel : BaseViewModel() {

    private val _bitmap by lazy { MutableLiveData<Bitmap>() }
    val bitmap: LiveData<Bitmap> = _bitmap

    override fun start() {
    }

    fun loadBitmap(resources: Resources) {
        viewModelScope.launch {
            flow {
                //注释掉的是拿取插件 皮肤下的 player_background_real.webp 图片 drawable 然后转成 bitmap
//                    val bitmap = ImageUtils.drawableToBitmap(ResourceUtils.getPluginDrawable(getSkinResources(), "player_background_real", SkinContains.packageName))
                val bitmap = ImageUtils.drawableToBitmap(ResourceUtils.getPluginDrawable(resources, "loading", SkinContains.packageNameHost))
                emit(bitmap)
            }.flowOnIOAndCatch().collect {
                _bitmap.value = it
            }
        }
    }
}