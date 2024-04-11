package com.wgllss.dynamic.host.lib.runtime

import com.wgllss.dynamic.host.lib.classloader.WXClassLoader
import java.io.File

class ContainerClassLoader(
    val containerKey: String? = null,
    val apkPath: String, optimizedDirectory: String,
    librarySearchPath: String?, parent: ClassLoader,
    private val childClassLoader: ClassLoader? = null
) : WXClassLoader(apkPath, File(optimizedDirectory).absolutePath, librarySearchPath, parent) {


    @Throws(ClassNotFoundException::class)
    override fun loadClass(className: String): Class<*> {
        var clazz = findLoadedClass(className)
        if (clazz == null) {
            try {
                clazz = findClass(className)
            } catch (e: ClassNotFoundException) {

            }
            if (clazz == null) {
                try {
                    clazz = super.loadClass(className)
                } catch (e: ClassNotFoundException) {
                    try {
                        clazz = childClassLoader!!.loadClass(className)
                    } catch (e: Exception) {
                        if (parent is ContainerClassLoader) {
                            clazz = parent.loadClass(className)
                        }
                    }
                }
            }
        }
        return clazz
    }
}