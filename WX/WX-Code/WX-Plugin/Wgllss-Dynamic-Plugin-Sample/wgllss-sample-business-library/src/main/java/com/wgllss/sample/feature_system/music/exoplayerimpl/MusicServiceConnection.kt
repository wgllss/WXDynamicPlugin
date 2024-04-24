package com.wgllss.sample.feature_system.music.exoplayerimpl

import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.lifecycle.MutableLiveData
import com.wgllss.ssmusic.features_system.extensions.duration
import com.wgllss.ssmusic.features_system.extensions.id
import com.wgllss.sample.feature_system.services.MusicService

class MusicServiceConnection private constructor(context: Context) {

    companion object {

        @Volatile
        private var instance: MusicServiceConnection? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: MusicServiceConnection(context).also { instance = it }
        }
    }

    val isConnected by lazy { MutableLiveData(false) }
//    val networkFailure = MutableLiveData<Boolean>()
//        .apply { postValue(false) }

    val rootMediaId by lazy { mediaBrowser.root }

    val playbackState by lazy {
        MutableLiveData<PlaybackStateCompat>()
            .apply { postValue(EMPTY_PLAYBACK_STATE) }
    }
    val nowPlaying by lazy {
        MutableLiveData<MediaMetadataCompat>()
            .apply { postValue(NOTHING_PLAYING) }
    }

//    val queueData: MutableLiveData<QueueData>

    val transportControls by lazy {
        mediaController.transportControls
    }

    private val mediaBrowserConnectionCallback by lazy { MediaBrowserConnectionCallback(context) }
    private val mediaBrowser by lazy {
        MediaBrowserCompat(context, ComponentName(context, MusicService::class.java), mediaBrowserConnectionCallback, null).apply {
            android.util.Log.e("MusicServiceConnection", "开始 connect")
            connect()
        }
    }

    fun startConnect() {
        mediaBrowser
    }

    private lateinit var mediaController: MediaControllerCompat

    fun subscribe(parentId: String, callback: MediaBrowserCompat.SubscriptionCallback) {
        mediaBrowser.subscribe(parentId, callback)
    }

    fun unsubscribe(parentId: String, callback: MediaBrowserCompat.SubscriptionCallback) {
        mediaBrowser.unsubscribe(parentId, callback)
    }

    fun sendCommand(command: String, parameters: Bundle?) =
        sendCommand(command, parameters) { _, _ -> }

    fun sendCommand(
        command: String,
        parameters: Bundle?,
        resultCallback: ((Int, Bundle?) -> Unit)
    ) = if (mediaBrowser.isConnected) {
        mediaController.sendCommand(command, parameters, object : ResultReceiver(Handler()) {
            override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
                resultCallback(resultCode, resultData)
            }
        })
        true
    } else {
        false
    }

    private inner class MediaBrowserConnectionCallback(private val context: Context) : MediaBrowserCompat.ConnectionCallback() {
        override fun onConnected() {
            android.util.Log.e("MusicServiceConnection", "onConnected")
            mediaController = MediaControllerCompat(context, mediaBrowser.sessionToken).apply {
                registerCallback(MediaControllerCallback())
            }

            isConnected.postValue(true)
        }

        override fun onConnectionSuspended() {
            isConnected.postValue(false)
        }

        override fun onConnectionFailed() {
            isConnected.postValue(false)
        }
    }

    private inner class MediaControllerCallback : MediaControllerCompat.Callback() {
        private var stateL = PlaybackStateCompat.STATE_NONE
        private var mediaID = ""
        private var duration = 0L

        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            state?.state.takeIf {
                it != stateL
            }?.let {
                stateL = it
                playbackState.postValue(state)
            }
        }

        override fun onMetadataChanged(it: MediaMetadataCompat?) {
            it?.takeIf {
                mediaID != it.id && duration != it.duration
            }?.let {
                mediaID = it.id ?: ""
                duration = it.duration
                nowPlaying.postValue(it)
            }
        }

        override fun onQueueChanged(queue: MutableList<MediaSessionCompat.QueueItem>?) {
        }

        override fun onExtrasChanged(extras: Bundle?) {
            super.onExtrasChanged(extras)
        }

        override fun onSessionDestroyed() {
            mediaBrowserConnectionCallback.onConnectionSuspended()
        }
    }
}

@Suppress("PropertyName")
val EMPTY_PLAYBACK_STATE: PlaybackStateCompat = PlaybackStateCompat.Builder()
    .setState(PlaybackStateCompat.STATE_NONE, 0, 0f)
    .build()

@Suppress("PropertyName")
val NOTHING_PLAYING: MediaMetadataCompat = MediaMetadataCompat.Builder()
    .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, "")
    .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, 0)
    .build()