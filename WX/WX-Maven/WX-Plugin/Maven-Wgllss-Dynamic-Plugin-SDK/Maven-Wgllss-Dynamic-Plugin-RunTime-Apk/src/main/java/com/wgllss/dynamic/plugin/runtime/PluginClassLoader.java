package com.wgllss.dynamic.plugin.runtime;


import com.wgllss.dynamic.host.lib.classloader.WXClassLoader;

public class PluginClassLoader extends WXClassLoader {
    private String privatePackage;

    public PluginClassLoader(String privatePackage, String dexPath, String optimizedDirectory, String librarySearchPath, ClassLoader parent) {
        super(dexPath, optimizedDirectory, librarySearchPath, parent);
        this.privatePackage = privatePackage;
    }

    @Override
    public Class<?> loadClass(String className) throws ClassNotFoundException {
        if (!className.contains(privatePackage)) {
            return parent.loadClass(className);
        }
        Class clazz = findLoadedClass(className);
        if (clazz == null) {
            try {
                clazz = findClass(className);
            } catch (ClassNotFoundException e) {
            }
            if (clazz == null) {
                try {
                    clazz = super.loadClass(className);
                } catch (ClassNotFoundException e) {
                    clazz = parent.loadClass(className);
                }
            }
        }
        return clazz;
    }
}
