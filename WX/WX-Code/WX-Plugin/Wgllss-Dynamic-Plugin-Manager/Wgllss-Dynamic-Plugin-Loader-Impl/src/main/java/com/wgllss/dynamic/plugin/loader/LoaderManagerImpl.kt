package com.wgllss.dynamic.plugin.loader

import android.content.Context
import com.wgllss.dynamic.host.lib.download.IDynamicDownLoadFace
import com.wgllss.dynamic.host.lib.loader_base.BaseLoaderManagerImpl

class LoaderManagerImpl : BaseLoaderManagerImpl() {

    override fun initDynamicLoader(
        context: Context, v: Int,
        clfd: Triple<String, String, Int>, clmd: Triple<String, String, Int>, cdlfd: Triple<String, String, Int>,
        mapDlu: MutableMap<String, Pair<String, Int>>,
        cotd: MutableMap<String, Int>,
        faceImpl: IDynamicDownLoadFace,
        isMustShowLoading: Boolean
    ) {
        android.util.Log.e("LoaderManagerImpl", "-----initDynamicLoader-----")
        super.initDynamicLoader(context, v, clfd, clmd, cdlfd, mapDlu, cotd, faceImpl, isMustShowLoading)
    }

    //    override fun initDynamicRunTime(context: Context, contentKey: String, fileAbsolutePath: String) {
//        super.initDynamicRunTime(context, contentKey, fileAbsolutePath)
//    }
//
//    override fun initHomeCreate(context: Context, classLoader: ClassLoader) {
//        super.initHomeCreate(context, classLoader)
//    }
//
    private fun test() {
        this.v
        this.clfd
        this.clmd
        this.cdlfd

        this.mapDlu
        this.cotd
        this.faceImpl
        this.mapOthers
        this.isShowLoadFlag
        this.libIsLoadComplete
        this.firstLoadSuccess
        this.hasOldFileDelete
        this.libLoadCount
    }
}