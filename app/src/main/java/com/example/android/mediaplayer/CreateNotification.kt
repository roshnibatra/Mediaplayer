package com.example.android.mediaplayer

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.session.MediaController
import android.media.session.MediaSession
import android.media.session.MediaSessionManager
import android.os.Build
import android.support.v4.media.session.MediaSessionCompat

import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

class MusicNotificationManager internal constructor(private val musicForeground: MusicForeground){

    private val CHANNEL_ID = "action_CHANNEL_ID"
    private val REQUEST_CODE = 100
    private lateinit var notification : Notification
    val notificationManager : NotificationManager
    var notificationBuilder: NotificationCompat.Builder? = null
    private var mediaSession: MediaSessionCompat? = null
    private var mediaSessionManager: MediaSessionManager? = null
    private var transportControls:MediaController.TransportControls? = null
    private val context:Context
    init {
        notificationManager = musicForeground.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        context= musicForeground.baseContext
    }

    private fun playerAction(action:String) :PendingIntent{
        val pauseIntent = Intent()
        pauseIntent.action = action

        return PendingIntent.getBroadcast(musicForeground,REQUEST_CODE,pauseIntent,PendingIntent.FLAG_UPDATE_CURRENT)
    }

    companion object {
        val ACTIONPAUSE = "action.PAUSE"
        val ACTIONPLAY = "actionPlay"
        val ACTIONSTOP = "actionStop"
        val NOTIFICATION_ID = 1
    }

        @SuppressLint("WrongConstant")
        private fun initMediaSession(){
            mediaSessionManager = context.getSystemService(Context.MEDIA_SESSION_SERVICE) as MediaSessionManager
            mediaSession = MediaSessionCompat(context,"Audio Player")
          //  transportControls = mediaSession!!.controller.transportControls
            mediaSession!!.isActive = true
            mediaSession!!.setFlags(MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS)
        }

    private fun notificationAction(action: String):NotificationCompat.Action {
        var icon:Int = R.drawable.play
        when(action) {
            ACTIONPAUSE ->icon = R.drawable.pause
            ACTIONPLAY -> icon = R.drawable.play
            ACTIONSTOP -> icon = R.drawable.stop
        }
        return NotificationCompat.Action.Builder(icon,action,playerAction(action)).build()
    }

    fun createNotification() : Notification{
        notificationBuilder = NotificationCompat.Builder(musicForeground,CHANNEL_ID)

        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            createNotificationChannel()
        }
        val openPlayerIntent = Intent(musicForeground,MusicActivity::class.java)
        openPlayerIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val contentIntent = PendingIntent.getActivity(musicForeground,REQUEST_CODE,openPlayerIntent,0)
        initMediaSession()
        notificationBuilder?.setShowWhen(false)
            ?.setSmallIcon(R.drawable.music)
            ?.setColor(ContextCompat.getColor(context,R.color.colorAccent))
            ?.setContentIntent(contentIntent)
            ?.addAction(notificationAction(ACTIONPAUSE))
            ?.addAction(notificationAction(ACTIONPLAY))
            ?.addAction(notificationAction(ACTIONSTOP))
            ?.setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

        notificationBuilder!!.setStyle(androidx.media.app.NotificationCompat.MediaStyle()
            .setMediaSession(mediaSession?.sessionToken)
            .setShowActionsInCompactView(0,1,2))
        return notificationBuilder!!.build()
    }


    @RequiresApi(value = 26)
    private fun createNotificationChannel() {
        if(notificationManager.getNotificationChannel(CHANNEL_ID) == null) {
            val notificationChannel= NotificationChannel(CHANNEL_ID,
            musicForeground.getString(R.string.app_name),
            NotificationManager.IMPORTANCE_LOW)

            notificationChannel.description = musicForeground.getString(R.string.app_name)
            notificationChannel.enableLights(false)
            notificationChannel.enableVibration(false)
            notificationChannel.setShowBadge(false)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }


}