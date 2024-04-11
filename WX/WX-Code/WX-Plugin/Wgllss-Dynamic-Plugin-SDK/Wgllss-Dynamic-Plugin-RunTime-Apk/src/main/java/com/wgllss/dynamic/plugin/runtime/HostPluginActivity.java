package com.wgllss.dynamic.plugin.runtime;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.wgllss.dynamic.host.lib.classloader.PluginKey;
import com.wgllss.dynamic.plugin.library.HostDelegate;

public class HostPluginActivity extends FragmentActivity {
    private PluginClassLoader pluginDexClassLoader;
    private Resources pluginResources;
    private HostDelegate mHostDelegate;
    private String skinPackageName;
    private boolean isFirst = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            //适配刘海屏
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            getWindow().setAttributes(lp);
        }
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            if (getIntent() != null) {
                String pluginApkPath = getIntent().getStringExtra(PluginKey.pluginApkPathKey);
                String activityName = getIntent().getStringExtra(PluginKey.activityNameKey);
                String privatePackage = getIntent().getStringExtra(PluginKey.privatePackageKey);
                pluginDexClassLoader = new PluginClassLoader(privatePackage, pluginApkPath, getDir("dex", Context.MODE_PRIVATE).getAbsolutePath(), null, getClassLoader());
                int flags = (PackageManager.GET_META_DATA | PackageManager.GET_ACTIVITIES | PackageManager.GET_SERVICES
                        | PackageManager.GET_PROVIDERS | PackageManager.GET_RECEIVERS);
                PackageManager packageManager = getApplicationContext().getPackageManager();
                PackageInfo packageInfo = packageManager.getPackageArchiveInfo(pluginApkPath, flags);
                ApplicationInfo applicationInfo = packageInfo.applicationInfo;
                applicationInfo.sourceDir = pluginApkPath;
                applicationInfo.publicSourceDir = pluginApkPath;
                try {
                    pluginResources = packageManager.getResourcesForApplication(applicationInfo);
                    skinPackageName = applicationInfo.packageName;

                    mHostDelegate = pluginDexClassLoader.getInterface(HostDelegate.class, activityName);
                    mHostDelegate.attachContext(this, pluginResources);
                    mHostDelegate.onCreate(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mHostDelegate.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mHostDelegate.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHostDelegate.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHostDelegate.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHostDelegate.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHostDelegate.onDestroy();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            if (isFirst) {
                isFirst = false;
                mHostDelegate.lazyIntValue();
            }
        }
    }
}
