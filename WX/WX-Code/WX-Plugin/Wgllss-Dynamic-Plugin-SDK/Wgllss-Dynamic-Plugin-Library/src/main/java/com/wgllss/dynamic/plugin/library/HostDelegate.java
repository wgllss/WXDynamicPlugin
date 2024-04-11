package com.wgllss.dynamic.plugin.library;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

public interface HostDelegate {

    void attachContext(FragmentActivity context, Resources resources);

    void onCreate(Bundle savedInstanceState);

    void onRestart();

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void lazyIntValue();
}
