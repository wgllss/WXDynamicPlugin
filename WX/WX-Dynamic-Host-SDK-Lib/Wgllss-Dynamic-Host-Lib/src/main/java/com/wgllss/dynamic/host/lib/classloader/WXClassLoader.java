package com.wgllss.dynamic.host.lib.classloader;

import dalvik.system.DexClassLoader;

public class WXClassLoader extends DexClassLoader {
    protected ClassLoader parent;

    public WXClassLoader(String dexPath, String optimizedDirectory, ClassLoader parent) {
        super(dexPath, optimizedDirectory, null, parent);
        this.parent = parent;
    }

    public WXClassLoader(String dexPath, String optimizedDirectory, String librarySearchPath, ClassLoader parent) {
        super(dexPath, optimizedDirectory, librarySearchPath, parent);
        this.parent = parent;
    }

    public <T> T getInterface(Class<T> clazz, String className) {
        try {
            Class<?> interfaceImplementClass = loadClass(className);
            Object interfaceImplement = interfaceImplementClass.newInstance();
            return clazz.cast(interfaceImplement);
        } catch (ClassNotFoundException | InstantiationException
                | ClassCastException | IllegalAccessException e) {
            return null;
        }
    }
}
