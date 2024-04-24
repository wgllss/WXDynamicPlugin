package com.wgllss.sample.features_ui.page.other2.viewmodel

import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.view.View
import androidx.core.net.toUri
import androidx.lifecycle.*
import com.wgllss.core.units.AppGlobals
import com.wgllss.core.viewmodel.BaseViewModel
import com.wgllss.sample.feature_system.globle.Constants.MEDIA_ARTNETWORK_URL_KEY
import com.wgllss.sample.feature_system.globle.Constants.MEDIA_AUTHOR_KEY
import com.wgllss.sample.feature_system.globle.Constants.MEDIA_ID_KEY
import com.wgllss.sample.feature_system.globle.Constants.MEDIA_TITLE_KEY
import com.wgllss.sample.feature_system.globle.Constants.MEDIA_URL_KEY
import com.wgllss.sample.feature_system.music.exoplayerimpl.MusicServiceConnection
import com.wgllss.sample.feature_system.music.extensions.currentPlayBackPosition
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class PlayModel : BaseViewModel() {

    private val musicServiceConnectionL by lazy { MusicServiceConnection.getInstance(AppGlobals.sApplication) }
    var isPlaying: Boolean = false
    val nowPlaying by lazy { MutableLiveData<MediaMetadataCompat>() }
    val playbackState by lazy { MutableLiveData<PlaybackStateCompat>() }
    private var updatePosition = true
    val mediaPosition by lazy { MutableLiveData(0L) }

    private val transportControls by lazy { musicServiceConnectionL.transportControls }

    //播放列表
    val liveData: MutableLiveData<MutableList<MediaBrowserCompat.MediaItem>> by lazy { MutableLiveData<MutableList<MediaBrowserCompat.MediaItem>>() }
    val rootMediaId: LiveData<String> by lazy {
        Transformations.map(musicServiceConnectionL.isConnected) { isConnected ->
            if (isConnected) {
                musicServiceConnectionL.rootMediaId
            } else {
                null
            }
        }
    }

    override fun start() {
        musicServiceConnectionL.startConnect()
    }

    private val subscriptionCallback by lazy {
        object : MediaBrowserCompat.SubscriptionCallback() {
            override fun onChildrenLoaded(parentId: String, children: MutableList<MediaBrowserCompat.MediaItem>) {
                liveData.value = children
            }
        }
    }

    fun subscribeByMediaID(mediaId: String) {
        musicServiceConnectionL.run {
            subscribe(mediaId, subscriptionCallback)
            playbackState.observeForever(playbackStateObserver)
            nowPlaying.observeForever(mediaMetadataObserver)
            checkPlaybackPosition()
        }
    }

    //从指定位置开始播放
    fun seek(pos: Long) {
        musicServiceConnectionL.transportControls.seekTo(pos)
    }

    fun playMp3() {
        val url = "https://storage.googleapis.com/automotive-media/Keys_To_The_Kingdom.mp3"
        val extras = Bundle().apply {
            putString(MEDIA_ID_KEY, "123156")
            putString(MEDIA_TITLE_KEY, "Keys To The Kingdom")
            putString(MEDIA_AUTHOR_KEY, "The 126ers")
            putString(MEDIA_ARTNETWORK_URL_KEY, "https://storage.googleapis.com/automotive-media/album_art_3.jpg")
            putString(MEDIA_URL_KEY, url)
        }
        transportControls.prepareFromUri(url.toUri(), extras)
    }

    fun onPlay(it: View) {
        isPlaying = !it.isSelected
        musicServiceConnectionL.transportControls.run {
            if (it.isSelected) pause() else {
                play()
            }
        }
    }

    fun onPlayNext(it: View) {
        musicServiceConnectionL.transportControls.skipToNext()
    }


    fun onPlayPrevious(it: View) {
        musicServiceConnectionL.transportControls.skipToPrevious()
    }

    private val playbackStateObserver = Observer<PlaybackStateCompat> {
        playbackState.postValue(it)
    }

    private val mediaMetadataObserver = Observer<MediaMetadataCompat> {
        nowPlaying.postValue(it)
    }

    private fun checkPlaybackPosition() {
        viewModelScope.launch {
            flow {
                while (updatePosition) {
                    val currPosition = playbackState?.value?.currentPlayBackPosition ?: 0
                    if (mediaPosition.value != currPosition)
                        mediaPosition.postValue(currPosition)
                    delay(998)
                }
                emit(0)
            }.flowOnIOAndCatch()
                .collect()
        }
    }

    override fun onCleared() {
        super.onCleared()
        musicServiceConnectionL.playbackState.removeObserver(playbackStateObserver)
        musicServiceConnectionL.nowPlaying.removeObserver(mediaMetadataObserver)
        updatePosition = false
    }
}