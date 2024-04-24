package com.wgllss.sample.feature_system.services

import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.ResultReceiver
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.core.content.ContextCompat
import androidx.media.MediaBrowserServiceCompat
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.ext.mediasession.TimelineQueueNavigator
import com.wgllss.core.units.SdkIntUtils
import com.wgllss.sample.feature_system.globle.Constants.MEDIA_ID_ROOT
import com.wgllss.sample.feature_system.music.notification.WXPlayerNotificationManager
import com.wgllss.host.skin.R
import com.wgllss.sample.feature_system.globle.Constants
import com.wgllss.sample.feature_system.music.notification.WXNotificationListener
import com.wgllss.sample.features_ui.playing.activity.NotificationTargetActivity
import com.wgllss.ssmusic.features_system.extensions.*

class MusicService : MediaBrowserServiceCompat(), MediaSessionConnector.PlaybackPreparer {
    private lateinit var notificationManager: WXPlayerNotificationManager

    private var isForegroundService = false
    private var currentMediaMetadataCompat: MediaMetadataCompat? = null
    private val playerListener by lazy { PlayerEventListener() }

    private val uAmpAudioAttributes by lazy {
        AudioAttributes.Builder()
            .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
            .setUsage(C.USAGE_MEDIA)
            .build()
    }

    val exoPlayer: ExoPlayer by lazy {
        ExoPlayer.Builder(this).build().apply {
            setAudioAttributes(uAmpAudioAttributes, true)
            setHandleAudioBecomingNoisy(true)
            addListener(playerListener)
        }
    }

    val mediaSession by lazy {
        MediaSessionCompat(this, getString(R.string.app_name))
            .apply {
                setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS)
                val pendingFlags = if (SdkIntUtils.isLollipop()) {
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                } else {
                    PendingIntent.FLAG_UPDATE_CURRENT
                }
                val sessionIntent = Intent(this@MusicService, NotificationTargetActivity::class.java)
                val sessionActivityPendingIntent = PendingIntent.getActivity(this@MusicService, 0, sessionIntent, pendingFlags)
                setSessionActivity(sessionActivityPendingIntent)
                isActive = true
            }
    }

    private val mediaSessionConnector by lazy {
        MediaSessionConnector(mediaSession).apply {
            setPlaybackPreparer(this@MusicService)
            setQueueNavigator(SSQueueNavigator(mediaSession))
        }
    }

    override fun onCreate() {
        super.onCreate()
        sessionToken = mediaSession.sessionToken
        notificationManager = WXPlayerNotificationManager(this, mediaSession, PlayerNotificationListener())
        notificationManager.showNotificationForPlayer(exoPlayer)
        mediaSessionConnector.setPlayer(exoPlayer)
        exoPlayer.clearMediaItems()
    }

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        exoPlayer.stop(true)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onGetRoot(clientPackageName: String, clientUid: Int, rootHints: Bundle?): BrowserRoot? {
        android.util.Log.e("MusicServiceConnection", "开始 onGetRoot")
        return BrowserRoot(MEDIA_ID_ROOT, null)
    }

    override fun onLoadChildren(parentId: String, result: Result<MutableList<MediaBrowserCompat.MediaItem>>) {
        result.detach()
        if (MEDIA_ID_ROOT == parentId) {
            android.util.Log.e("MusicServiceConnection", "开始 onLoadChildren")
            //todo 查询 到列表
            result.sendResult(mutableListOf())
        }
    }

    override fun onCommand(player: Player, command: String, extras: Bundle?, cb: ResultReceiver?) = false

    override fun getSupportedPrepareActions() = PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID or
            PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID or
            PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH or
            PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH or
            PlaybackStateCompat.ACTION_PLAY_FROM_URI or
            PlaybackStateCompat.ACTION_PREPARE_FROM_URI or
            PlaybackStateCompat.ACTION_SEEK_TO or
            PlaybackStateCompat.ACTION_SKIP_TO_NEXT or
            PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS

    override fun onPrepare(playWhenReady: Boolean) {
    }

    override fun onPrepareFromMediaId(mediaId: String, playWhenReady: Boolean, extras: Bundle?) {
    }

    override fun onPrepareFromSearch(query: String, playWhenReady: Boolean, extras: Bundle?) {
    }

    override fun onPrepareFromUri(uri: Uri, playWhenReady: Boolean, extras: Bundle?) {
        extras?.run {
            preparePlay(
                getString(Constants.MEDIA_ID_KEY) ?: "",
                getString(Constants.MEDIA_TITLE_KEY) ?: "",
                getString(Constants.MEDIA_AUTHOR_KEY) ?: "",
                getString(Constants.MEDIA_ARTNETWORK_URL_KEY) ?: "",
                getString(Constants.MEDIA_URL_KEY) ?: ""
            )
        }
    }

    private fun preparePlay(mediaId: String, musicTitle: String, author: String, pic: String, url: String) {
        val mediaMetadataCompat = MediaMetadataCompat.Builder().apply {
            id = mediaId
            title = musicTitle
            mediaUri = url
            artist = author
            albumArtUri = pic
            downloadStatus = MediaDescriptionCompat.STATUS_NOT_DOWNLOADED
        }.build().apply {
            description.extras?.putAll(bundle)
        }
        currentMediaMetadataCompat = mediaMetadataCompat
        exoPlayer.stop()
        exoPlayer.playWhenReady = true
        exoPlayer.setMediaItem(mediaMetadataCompat.toMediaItem())
        exoPlayer.prepare()
    }

    private inner class PlayerEventListener : Player.Listener {

        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            when (playbackState) {
                Player.STATE_BUFFERING, Player.STATE_READY -> {
                    if (playbackState == Player.STATE_READY) {
                        if (playWhenReady) {
                        }
                        setPlaybackState(PlaybackStateCompat.STATE_PLAYING)
                    }
                    notificationManager.showNotificationForPlayer(exoPlayer)
                }
                Player.STATE_ENDED -> {
                    playNext()
                }
                else -> {
//                    notificationManager.hideNotification()
                }
            }
        }

        private fun setPlaybackState(playbackState: Int) {
            val speed = exoPlayer.playbackParameters?.speed ?: 1.0f
            mediaSession.setPlaybackState(PlaybackStateCompat.Builder().apply {
                setState(playbackState, exoPlayer.contentPosition, speed)
                    .setActions(supportedPrepareActions)
                setExtras(Bundle().apply {
                    putLong(MediaMetadataCompat.METADATA_KEY_DURATION, exoPlayer.duration)
                })
            }.build())
        }

        override fun onPlayerError(error: PlaybackException) {
            super.onPlayerError(error)
            playNext()
        }
    }

    private inner class SSQueueNavigator(mediaSession: MediaSessionCompat) : TimelineQueueNavigator(mediaSession) {
        override fun getMediaDescription(player: Player, windowIndex: Int): MediaDescriptionCompat = currentMediaMetadataCompat?.description ?: MediaDescriptionCompat.Builder().build()

        override fun onSkipToNext(player: Player) = playNext()

        override fun onSkipToPrevious(player: Player) = playPrevious()

        override fun getSupportedQueueNavigatorActions(player: Player) = PlaybackStateCompat.ACTION_SKIP_TO_NEXT or PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
    }

    /**
     * Listen for notification events.
     */
    private inner class PlayerNotificationListener : WXNotificationListener {
        override fun onNotificationPosted(notificationId: Int, notification: Notification, ongoing: Boolean) {
            if (ongoing && !isForegroundService) {
                ContextCompat.startForegroundService(this@MusicService, Intent(this@MusicService, this@MusicService.javaClass))
                startForeground(notificationId, notification)
                isForegroundService = true
            }
        }

        override fun onNotificationCancelled(notificationId: Int, dismissedByUser: Boolean) {
            stopForeground(true)
            isForegroundService = false
            stopSelf()
        }
    }

    protected open fun playNext() {

    }

    protected open fun playPrevious() {

    }
}