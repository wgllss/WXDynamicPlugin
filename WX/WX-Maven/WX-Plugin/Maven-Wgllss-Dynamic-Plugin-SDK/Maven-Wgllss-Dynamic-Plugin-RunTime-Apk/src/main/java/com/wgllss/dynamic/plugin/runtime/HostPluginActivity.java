package com.wgllss.dynamic.plugin.runtime;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wgllss.core.activity.BaseActivity;
import com.wgllss.dynamic.host.lib.classloader.PluginKey;
import com.wgllss.dynamic.runtime.library.WXHostActivityDelegate;

public class HostPluginActivity extends BaseActivity {
    private PluginClassLoader pluginDexClassLoader;
    private Resources pluginResources;
    private WXHostActivityDelegate mHostDelegate;
    private String skinPackageName;
    private boolean isFirst = true;

    private String pluginApkPath = "";
    private String activityName = "";
    private String privatePackage = "";

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(PluginKey.pluginApkPathKey, pluginApkPath);
        outState.putString(PluginKey.activityNameKey, activityName);
        outState.putString(PluginKey.privatePackageKey, privatePackage);
        if (mHostDelegate != null)
            mHostDelegate.onSaveInstanceState(outState);
    }

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
                pluginApkPath = getIntent().getStringExtra(PluginKey.pluginApkPathKey);
                activityName = getIntent().getStringExtra(PluginKey.activityNameKey);
                privatePackage = getIntent().getStringExtra(PluginKey.privatePackageKey);
            } else {
                throw new RuntimeException("There are no pluginApkPath, activityName, or privatePackage parameters in the intent");
            }
        } else {
            pluginApkPath = savedInstanceState.getString(PluginKey.pluginApkPathKey);
            activityName = savedInstanceState.getString(PluginKey.activityNameKey);
            privatePackage = savedInstanceState.getString(PluginKey.privatePackageKey);
        }

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

            mHostDelegate = pluginDexClassLoader.getInterface(WXHostActivityDelegate.class, activityName);
            mHostDelegate.attachContext(this, pluginResources);
            mHostDelegate.onCreate(savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mHostDelegate != null)
            mHostDelegate.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mHostDelegate != null)
            mHostDelegate.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mHostDelegate != null)
            mHostDelegate.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mHostDelegate != null)
            mHostDelegate.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mHostDelegate != null)
            mHostDelegate.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHostDelegate != null)
            mHostDelegate.onDestroy();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            if (isFirst) {
                isFirst = false;
                mHostDelegate.lazyInitValue();
            }
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mHostDelegate != null)
            mHostDelegate.onConfigurationChanged(newConfig);
    }

    @Override
    public void initControl(Bundle savedInstanceState) {

    }

    @Override
    public void bindEvent() {

    }

    @Override
    public void initValue() {

    }
}
