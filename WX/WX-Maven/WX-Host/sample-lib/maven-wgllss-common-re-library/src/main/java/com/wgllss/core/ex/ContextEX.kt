package com.wgllss.core.ex

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.wgllss.core.re.R


fun Context.launchActivity(intent: Intent) {
    startActivity(intent)
    if (this is Activity)
        overridePendingTransition(R.anim.anim_alpha_121, R.anim.anim_alpha_121)
}

fun Activity.finishActivity() {
    finish()
    overridePendingTransition(R.anim.anim_alpha_121, R.anim.anim_alpha_121)
}
