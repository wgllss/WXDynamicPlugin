package com.wgllss.dynamic.runtime.library;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

public interface WXHostActivityDelegate {

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
