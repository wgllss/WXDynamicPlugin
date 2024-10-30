package com.wgllss.core.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialog
import androidx.core.app.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
//import androidx.lifecycle.OnLifecycleEvent
import com.wgllss.core.re.R

class CommonLoadingView constructor(context: Context) : Dialog(context, R.style.Loading) {

    private lateinit var txt_loading_text: TextView

//    constructor (context: Context) : super(context, R.style.Loading) {
//        setContentView(R.layout.common_loading)
//        window?.run {
//            txt_loading_text = findViewById(R.id.txt_loading_text)
//            attributes.gravity = Gravity.CENTER
//        }
//    }

    init {

//        context.takeIf {
//            it is ComponentActivity
//        }?.let {
////            (it as ComponentActivity).lifecycle.addObserver(this)
////            lifecycle =  (it as ComponentActivity).lifecycle
//        }


        setContentView(R.layout.common_loading)
        window?.run {
            txt_loading_text = findViewById(R.id.txt_loading_text)
            attributes.gravity = Gravity.CENTER
        }
    }

    fun show(showText: String) {
        txt_loading_text?.apply {
            if (showText.isNotEmpty()) {
                text = showText
                visibility = View.VISIBLE
            } else {
                visibility = View.GONE
            }
        }
        if (!isShowing()) {
            show()
        }
    }

//    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
//    private fun onDestory() {
//        if (isShowing) {
//            dismiss()
//        }
//    }
}