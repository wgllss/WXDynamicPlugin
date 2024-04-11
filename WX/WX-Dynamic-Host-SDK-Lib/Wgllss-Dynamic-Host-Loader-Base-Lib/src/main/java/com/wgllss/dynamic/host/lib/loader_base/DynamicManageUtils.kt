package com.wgllss.dynamic.host.lib.loader_base

import android.content.Context
import com.wgllss.dynamic.host.lib.classloader.WXClassLoader
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

object DynamicManageUtils {

    fun getDlfn(k: String, v: Int): String {
        return StringBuilder().append(k).append("_").append(v).toString()
    }

    fun getDxFile(context: Context, dir: String, fileName: String) = File(StringBuilder(context.filesDir.absolutePath).append(File.separator).append(dir).append(File.separator).append(fileName).toString())

    fun copyFileFromAssetsToSD(context: Context, desFile: File, fileName: String): Boolean {
        var copyIsFinish = false
        try {
            val fileDir = File(desFile.parent)
            if (!fileDir.exists()) {
                fileDir.mkdirs()
            }
            val inputStream: InputStream = context.assets.open(fileName)
            val fos = FileOutputStream(desFile)
            val temp = ByteArray(1024)
            var i = 0
            while (inputStream.read(temp).also { i = it } > 0) {
                fos.write(temp, 0, i) // 写入到文件
            }
            fos.close()
            inputStream.close()
            copyIsFinish = true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return copyIsFinish
    }

    fun <T> getClassImpl(cls: Class<T>, className: String, existsFile: File, optPath: String, parent: ClassLoader, ifCondition: Boolean, default: T): T {
        return if (ifCondition)
            WXClassLoader(existsFile.absolutePath, optPath, parent).getInterface(cls, className)
        else default
    }

}