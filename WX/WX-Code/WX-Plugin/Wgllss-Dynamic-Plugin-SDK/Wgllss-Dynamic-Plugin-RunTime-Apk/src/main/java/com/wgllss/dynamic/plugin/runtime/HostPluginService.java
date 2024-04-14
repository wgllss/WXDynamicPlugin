package com.wgllss.dynamic.plugin.runtime;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.wgllss.dynamic.host.lib.classloader.PluginKey;
import com.wgllss.dynamic.runtime.library.WXHostServiceDelegate;

import java.util.HashMap;
import java.util.LinkedHashMap;

public abstract class HostPluginService extends Service {

    private PluginClassLoader pluginDexClassLoader;
    private HashMap<String, WXHostServiceDelegate> map;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        if (map != null) {
            for (WXHostServiceDelegate service : map.values()) {
                service.onStart(intent, startId);
            }
        }
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initPluginService(intent);
        String serviceName = intent.getStringExtra(PluginKey.serviceNameKey);
        if (map.containsKey(serviceName)) {
            map.get(serviceName).onStartCommand(intent, flags, startId);
            map.get(serviceName).onStart(intent, startId);
        }
        return onStartCommand();
    }

    public abstract int onStartCommand();

//    @Override
//    public boolean onUnbind(Intent intent) {
//        String serviceName = intent.getStringExtra(PluginKey.serviceNameKey);
//        if (map.containsKey(serviceName)) {
//            map.get(serviceName).onUnbind(intent);
//            String bindKey = new StringBuilder(serviceName).append("_bind").toString();
//            map.remove(bindKey);
//        }
//        return super.onUnbind(intent);
//    }

    @Override
    public void onDestroy() {
        if (map != null) {
            for (WXHostServiceDelegate service : map.values()) {
                service.onDestroy();
            }
        }
        super.onDestroy();
    }

    private void initPluginService(Intent intent) {
        try {
            if (map == null) {
                map = new LinkedHashMap();
            }
            if (intent != null) {
                String serviceName = intent.getStringExtra(PluginKey.serviceNameKey);
                if (!map.containsKey(serviceName)) {
                    String pluginApkPath = intent.getStringExtra(PluginKey.pluginApkPathKey);
                    String privatePackage = intent.getStringExtra(PluginKey.privatePackageKey);
                    pluginDexClassLoader = new PluginClassLoader(privatePackage, pluginApkPath, getDir("dex", Context.MODE_PRIVATE).getAbsolutePath(), null, getClassLoader());
                    WXHostServiceDelegate serviceDelegate = pluginDexClassLoader.getInterface(WXHostServiceDelegate.class, serviceName);
                    serviceDelegate.attachBaseContext(this);
                    serviceDelegate.onCreate();
                    map.put(serviceName, serviceDelegate);
                }
//                String bindKey = new StringBuilder(serviceName).append("_bind").toString();
//                if (isOnBind && !map.containsKey(bindKey)) {
//                    map.get(serviceName).onBind(intent);
//                    map.put(bindKey, null);
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
