package com.wgllss.sample.features_ui.page.other2.activity

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.wgllss.core.ex.finishActivity
import com.wgllss.core.ex.getIntToDip
import com.wgllss.core.units.ResourceUtils
import com.wgllss.sample.features_ui.page.base.BasePluginActivity
import com.wgllss.sample.features_ui.page.base.SkinContains
import com.wgllss.sample.features_ui.page.other2.viewmodel.Other2ViewModel

class VideoActivity : BasePluginActivity<Other2ViewModel>("activity_video") {
    private var player: ExoPlayer? = null

    private lateinit var playerView: StyledPlayerView
    private lateinit var layout_content: FrameLayout
    private lateinit var img_back: ImageView
    private lateinit var layout_title: FrameLayout
    private lateinit var title: TextView

    private var startItemIndex: Int = 0
    private var startPosition: Long = 0

    private var url = "https://stream7.iqilu.com/10339/upload_transcode/202002/18/20200218093206z8V1JuPlpe.mp4"

    companion object {
        private const val KEY_ITEM_INDEX = "item_index"
        private const val KEY_POSITION = "position"
    }

    override fun initControl(savedInstanceState: Bundle?) {
        super.initControl(savedInstanceState)
        layout_content = findViewById(getPluginID("layout_content"))
        playerView = StyledPlayerView(activity).apply {
            layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        }
        layout_content.addView(playerView)

        layout_title = findViewById(getPluginID("layout_title"))
        img_back = findViewById(getPluginID("img_back"))
        title = findViewById(getPluginID("title"))
        window.setBackgroundDrawable(null)
        if (savedInstanceState != null) {
            startItemIndex = savedInstanceState.getInt(KEY_ITEM_INDEX)
            startPosition = savedInstanceState.getLong(KEY_POSITION)
        }
    }

    override fun bindEvent() {
        super.bindEvent()
        playerView.setFullscreenButtonClickListener {
            requestedOrientation = if (it) {
                window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            } else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        }
        playerView.setControllerVisibilityListener(StyledPlayerView.ControllerVisibilityListener {
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                layout_title.layoutParams.height = activity.getIntToDip(45f).toInt()
            }
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                layout_title.layoutParams.height = activity.getIntToDip(81f).toInt()
            }
            layout_title.visibility = it
        })
        img_back.setOnClickListener {
            activity.finishActivity()
        }
    }

    override fun initValue() {
        title.text = "示例视频 在线视频"
        playerView.setShowFastForwardButton(false)
        playerView.setShowNextButton(false)
        playerView.setShowPreviousButton(false)
        playerView.setShowRewindButton(false)
        layout_title.bringToFront()
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        val dataSourceFactory = DefaultDataSourceFactory(activity)
        return ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(uri))
    }


    private fun initializePlayer(): Boolean {
        if (player == null) {
            val playerBuilder = ExoPlayer.Builder(activity)
                .setMediaSourceFactory(DefaultMediaSourceFactory(activity))
                .setRenderersFactory(DefaultRenderersFactory(activity))
                .setLoadControl(DefaultLoadControl())
            player = playerBuilder.build()
            player?.setAudioAttributes(AudioAttributes.DEFAULT, true)
            player?.playWhenReady = true
            playerView.player = player
        }
        val playUri: Uri = Uri.parse(url)
        val mediaSource: MediaSource = buildMediaSource(playUri)
        player?.prepare(mediaSource, true, false)
        player?.seekTo(startPosition)
        return true
    }

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT > 23) {
            initializePlayer()
            if (playerView != null) {
                playerView.onResume()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT <= 23 || player == null) {
            initializePlayer()
            if (playerView != null) {
                playerView.onResume()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (Build.VERSION.SDK_INT <= 23) {
            if (playerView != null) {
                playerView.onPause()
            }
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Build.VERSION.SDK_INT > 23) {
            if (playerView != null) {
                playerView.onPause()
            }
            releasePlayer()
        }
    }

    private fun releasePlayer() {
        if (player != null) {
            updateStartPosition()
            player?.release()
            player = null
            playerView.player = null
        }
        playerView.adViewGroup.removeAllViews()
    }

    private fun updateStartPosition() {
        if (player != null) {
            startItemIndex = player!!.currentMediaItemIndex
            startPosition = Math.max(0, player!!.contentPosition)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        updateStartPosition()
        outState.putInt(KEY_ITEM_INDEX, startItemIndex)
        outState.putLong(KEY_POSITION, startPosition)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (layout_title.visibility == View.VISIBLE)
                layout_title.layoutParams.height = activity.getIntToDip(45f).toInt()
        }
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (layout_title.visibility == View.VISIBLE)
                layout_title.layoutParams.height = activity.getIntToDip(81f).toInt()
        }
    }

    override fun onChangeSkin(skinRes: Resources) {
        ResourceUtils.run {
            setImageDrawable(skinRes, "ic_baseline_arrow_back_24", SkinContains.packageName, img_back)
            setBackgroundColor(skinRes, "black", SkinContains.packageName, layout_content)
            setTextColor(skinRes, "white", SkinContains.packageName, title)
        }
    }
}