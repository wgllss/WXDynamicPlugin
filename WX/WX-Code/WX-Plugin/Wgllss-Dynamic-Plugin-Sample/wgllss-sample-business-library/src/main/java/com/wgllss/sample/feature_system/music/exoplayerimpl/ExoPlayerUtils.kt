package com.wgllss.sample.feature_system.music.exoplayerimpl

import android.content.Context
import com.wgllss.host.skin.R
import kotlin.math.floor

object ExoPlayerUtils {
    fun timestampToMSS(context: Context, position: Long): String {
        val totalSeconds = floor(position / 1E3).toInt()
        val minutes = totalSeconds / 60
        val remainingSeconds = totalSeconds - (minutes * 60)
        return if (position < 0) context.getString(R.string.duration_unknown)
        else context.getString(R.string.duration_format).format(minutes, remainingSeconds)
    }
}