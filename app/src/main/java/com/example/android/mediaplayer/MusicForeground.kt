package com.example.android.mediaplayer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import androidx.media.MediaBrowserServiceCompat


class MusicForeground : MediaBrowserServiceCompat() {
    private lateinit var mediaPlayer: MediaPlayer
    private var musicNotificationManager:MusicNotificationManager? = null
    private var notificationActionReceiver :NotificationReceiver? = null


    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot? {
        return null
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        return
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.mittar)
        mediaPlayer.isLooping
        mediaPlayer.setVolume(10f, 10f)
    }

    fun onPause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            musicNotificationManager!!.notificationManager.notify(MusicNotificationManager.NOTIFICATION_ID,musicNotificationManager!!
                .createNotification())
        }

    }

    private inner class NotificationReceiver :BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent?.action
            if(action != null) {
                when(action) {
                    MusicNotificationManager.ACTIONPAUSE ->onPause()
                    MusicNotificationManager.ACTIONPLAY -> play()
                    MusicNotificationManager.ACTIONSTOP -> onDestroy()

                }
            }
        }

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (intent?.action.equals("PLAY")) {
            mediaPlayer.start()
        }
        if (intent?.action.equals("PAUSE")) {
            mediaPlayer.pause()
        }

        return START_STICKY
    }

    fun play() {
        if( !mediaPlayer.isPlaying)
        mediaPlayer.start()
        musicNotificationManager?.notificationManager?.notify(MusicNotificationManager.NOTIFICATION_ID,musicNotificationManager!!
            .createNotification())
    }


    override fun onDestroy() {
        super.onDestroy()

        mediaPlayer.stop()
        musicNotificationManager = null
        mediaPlayer.release()
    }


}