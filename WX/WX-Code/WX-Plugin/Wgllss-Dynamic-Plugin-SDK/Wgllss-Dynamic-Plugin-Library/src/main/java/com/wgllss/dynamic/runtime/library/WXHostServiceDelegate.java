package com.wgllss.dynamic.runtime.library;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public interface WXHostServiceDelegate {

    void attachBaseContext(Context newBase);

    IBinder onBind(Intent intent);

    void onCreate();

    void onStart(Intent intent, int startId);

    int onStartCommand(Intent intent, int flags, int startId);

    boolean onUnbind(Intent intent);

    void onDestroy();

}
