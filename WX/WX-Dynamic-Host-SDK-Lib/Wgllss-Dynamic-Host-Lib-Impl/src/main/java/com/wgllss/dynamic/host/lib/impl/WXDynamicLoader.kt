package com.wgllss.dynamic.host.lib.impl

import android.content.Context
import android.text.TextUtils
import com.wgllss.dynamic.host.lib.constant.DynamicPluginConstant.dldir
import com.wgllss.dynamic.host.lib.constant.DynamicPluginConstant.versionFile
import com.wgllss.dynamic.host.lib.download.IDynamicDownLoadFace
import com.wgllss.dynamic.host.lib.loader.ILoaderManager
import com.wgllss.dynamic.host.lib.loader_base.DynamicManageUtils.getClassImpl
import com.wgllss.dynamic.host.lib.loader_base.DynamicManageUtils.getDlfn
import com.wgllss.dynamic.host.lib.loader_base.DynamicManageUtils.getDxFile
import com.wgllss.dynamic.host.lib.version.ILoaderVersion
import java.io.File

class WXDynamicLoader private constructor() {

    lateinit var context: Context

    lateinit var loader: ILoaderManager

    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { WXDynamicLoader() }
    }

    fun installPlugin(context: Context, face: IDynamicDownLoadFace, vImpl: ILoaderVersion) {
        this.context = context
        val file = getDxFile(context, dldir, versionFile)
        val optPath = context.getDir("dex", Context.MODE_PRIVATE).absolutePath
        val parent = javaClass.classLoader
        File(file.parent).takeIf { !it.exists() }?.mkdirs()
        getClassImpl(ILoaderVersion::class.java, face.getLoadVersionClassName(), file, optPath, parent, file.exists(), vImpl)?.run {
            val fileCdldd = getDxFile(context, dldir, getDlfn(getCdlfd().second, getCdlfd().third))
            val faceImpl = getClassImpl(IDynamicDownLoadFace::class.java, getCdlfd().first, fileCdldd, optPath, parent, fileCdldd.exists() && !TextUtils.isEmpty(getCdlfd().first), face)
            val fileClmd = getDxFile(context, dldir, getDlfn(getClmd().second, getClmd().third))
            loader = getClassImpl(ILoaderManager::class.java, getClmd().first, fileClmd, optPath, parent, fileClmd.exists() && !TextUtils.isEmpty(getClmd().first), WXLoaderManagerImpl())
            loader?.initDynamicLoader(context, getV(), getClfd(), getClmd(), getCdlfd(), getMapDLU(), getOthers(), faceImpl, isMustShowLoading())
        }
    }
}