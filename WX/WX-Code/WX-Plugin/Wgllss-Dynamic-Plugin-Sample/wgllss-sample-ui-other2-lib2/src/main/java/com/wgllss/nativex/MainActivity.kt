package com.wgllss.nativex

import android.content.Context
import android.widget.Toast

class MainActivity {

    external fun stringFromJNI(): String

    external fun callJNI()

    external fun stringFromJNIwithParameter(str: String): String

    external fun callNativeCallJavaMethod()

    external fun callNativeCallJavaField()

    external fun callNativeWithCallBack(callBack: NativeCallBack)

    external fun dynamicRegisterCallBack(callBack: NativeCallBack)

    external fun jniint(): Boolean

    external fun getKey(): String

    fun toast(context: Context) {
        Toast.makeText(context, stringFromJNI(), Toast.LENGTH_LONG).show()
    }
}