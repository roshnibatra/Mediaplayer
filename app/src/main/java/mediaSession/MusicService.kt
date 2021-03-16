package mediaSession
//
//import android.R
//import android.app.PendingIntent
//import android.content.ComponentName
//import android.content.Intent
//import android.graphics.BitmapFactory
//import android.media.MediaPlayer
//import android.os.Bundle
//import android.os.PowerManager
//import android.support.v4.media.MediaBrowserCompat
//import android.support.v4.media.MediaMetadataCompat
//import android.support.v4.media.session.MediaSessionCompat
//import android.support.v4.media.session.PlaybackStateCompat
//import androidx.core.app.NotificationCompat
//import androidx.core.app.NotificationManagerCompat
//import androidx.media.MediaBrowserServiceCompat
//import androidx.media.session.MediaButtonReceiver
//import java.io.IOException
//
//
//class MusicService : MediaBrowserServiceCompat() ,MediaPlayer.OnCompletionListener {
//    private lateinit var mediaPlayer: MediaPlayer
//    private lateinit var mediaSessionCompat: MediaSessionCompat
//    private var isPlaying: Boolean = true
//
//    override fun onGetRoot(
//        clientPackageName: String,
//        clientUid: Int,
//        rootHints: Bundle?
//    ): BrowserRoot? {
//        return null
//    }
//
//    override fun onLoadChildren(
//        parentId: String,
//        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
//    ) {
//        return
//    }
//
//
//    private fun showPlayingNotification() {
//        val builder: NotificationCompat.Builder =
//            (MediaStyleHelper(this@MusicService, mediaSessionCompat)
//                ?: return) as NotificationCompat.Builder
//        builder.addAction(
//            NotificationCompat.Action(
//                R.drawable.ic_media_play,
//                "Pause",
//                MediaButtonReceiver.buildMediaButtonPendingIntent(
//                    this,
//                    PlaybackStateCompat.ACTION_PLAY_PAUSE
//                )
//            )
//        )
//        builder.setStyle(
//            androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(0)
//                .setMediaSession(mediaSessionCompat.getSessionToken())
//        )
//        builder.setSmallIcon(R.drawable.stat_notify_chat)
//        NotificationManagerCompat.from(this@MusicService).notify(1, builder.build())
//    }
//
//    private fun showPausedNotification() {
//        val builder: NotificationCompat.Builder = (MediaStyleHelper(this,mediaSessionCompat)
//            ?: return) as NotificationCompat.Builder
//        builder.addAction(
//            NotificationCompat.Action(
//                R.drawable.ic_media_pause,
//                "Play",
//                MediaButtonReceiver.buildMediaButtonPendingIntent(
//                    this,
//                    PlaybackStateCompat.ACTION_PLAY_PAUSE
//                )
//            )
//        )
//        builder.setStyle(
//            androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(0)
//                .setMediaSession(mediaSessionCompat.getSessionToken())
//        )
//        builder.setSmallIcon(R.drawable.ic_media_pause)
//        NotificationManagerCompat.from(this).notify(1, builder.build())
//    }
//
//    override fun onCreate() {
//        super.onCreate()
//        initMediaPlayer()
//        initMediaSession()
//    }
//
//    private fun initMediaSession() {
//        val mediaButtonReceiver = ComponentName(
//            applicationContext,
//            MediaButtonReceiver::class.java
//        )
//        mediaSessionCompat =
//            MediaSessionCompat(applicationContext, "Tag", mediaButtonReceiver, null)
//        mediaSessionCompat.setCallback(mMediaSessionCallback)
//        mediaSessionCompat.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS)
//        val mediaButtonIntent = Intent(Intent.ACTION_MEDIA_BUTTON)
//        mediaButtonIntent.setClass(this@MusicService, MediaButtonReceiver::class.java)
//        val pendingIntent = PendingIntent.getBroadcast(
//            this@MusicService,
//            0,
//            mediaButtonIntent,
//            0
//        )
//        mediaSessionCompat.setMediaButtonReceiver(pendingIntent)
//        setSessionToken(mediaSessionCompat.getSessionToken())
//    }
//
//    private fun initMediaPlayer() {
//        mediaPlayer = MediaPlayer()
//        mediaPlayer.setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
////            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
//        mediaPlayer.setVolume(1.0f, 1.0f)
//    }
//
//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        MediaButtonReceiver.handleIntent(mediaSessionCompat, intent)
//        return super.onStartCommand(intent, flags, startId)
//    }
//
//    val mMediaSessionCallback = object: MediaSessionCompat.Callback() {
//        override fun onPlay() {
//            super.onPlay()
////            if (!successfullyRetrievedAudioFocus())
////            {
////                return
////            }
//            mediaSessionCompat.setActive(true)
//            setMediaPlaybackState(PlaybackStateCompat.STATE_PLAYING)
//            showPlayingNotification()
//            mediaPlayer.start()
//        }
//
//        override fun onPause() {
//            super.onPause()
//            if (mediaPlayer.isPlaying()) {
//                mediaPlayer.pause()
//                setMediaPlaybackState(PlaybackStateCompat.STATE_PAUSED)
//                showPausedNotification()
//            }
//        }
//
//
//        override fun onPlayFromMediaId(mediaId: String?, extras: Bundle?) {
//            super.onPlayFromMediaId(mediaId, extras)
//            try {
//                val afd = resources.openRawResourceFd(
//                    Integer.valueOf(
//                        mediaId!!
//                    )
//                ) ?: return
//                try {
//                    mediaPlayer.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
//                } catch (e: IllegalStateException) {
//                    mediaPlayer.release()
//                    initMediaPlayer()
//                    mediaPlayer.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
//                }
//                afd.close()
//                initMediaSessionMetadata()
//            } catch (e: IOException) {
//                return
//            }
//            try {
//                mediaPlayer.prepare()
//            } catch (e: IOException) {
//            }
//
//            //Work with extras here if you want
//        }
//
//        private fun initMediaSessionMetadata() {
//            val metadataBuilder = MediaMetadataCompat.Builder()
//            //Notification icon in card
//            metadataBuilder.putBitmap(
//                MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON, BitmapFactory.decodeResource(
//                    resources, R.mipmap.sym_def_app_icon
//                )
//            )
//            metadataBuilder.putBitmap(
//                MediaMetadataCompat.METADATA_KEY_ALBUM_ART, BitmapFactory.decodeResource(
//                    resources, R.mipmap.sym_def_app_icon
//                )
//            )
//
//            //lock screen icon for pre lollipop
//            metadataBuilder.putBitmap(
//                MediaMetadataCompat.METADATA_KEY_ART, BitmapFactory.decodeResource(
//                    resources, R.mipmap.sym_def_app_icon
//                )
//            )
//            metadataBuilder.putString(
//                MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE,
//                "Display Title"
//            )
//            metadataBuilder.putString(
//                MediaMetadataCompat.METADATA_KEY_DISPLAY_SUBTITLE,
//                "Display Subtitle"
//            )
//            metadataBuilder.putLong(MediaMetadataCompat.METADATA_KEY_TRACK_NUMBER, 1)
//            metadataBuilder.putLong(MediaMetadataCompat.METADATA_KEY_NUM_TRACKS, 1)
//            mediaSessionCompat.setMetadata(metadataBuilder.build())
//        }
//
//
//        private fun setMediaPlaybackState(state: Int) {
//            val playbackstateBuilder = PlaybackStateCompat.Builder()
//            if (state == PlaybackStateCompat.STATE_PLAYING) {
//                playbackstateBuilder.setActions(PlaybackStateCompat.ACTION_PLAY_PAUSE or PlaybackStateCompat.ACTION_PAUSE)
//            } else {
//                playbackstateBuilder.setActions(PlaybackStateCompat.ACTION_PLAY_PAUSE or PlaybackStateCompat.ACTION_PLAY)
//            }
//            playbackstateBuilder.setState(state, PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, 0f)
//            mediaSessionCompat.setPlaybackState(playbackstateBuilder.build())
//        }
//
//
//    }
//    override fun onDestroy() {
//        super.onDestroy()
//
//        mediaPlayer.stop()
//        mediaPlayer.release()
//    }
//
//
//    override fun onCompletion(mp: MediaPlayer?) {
//        if( mediaPlayer != null ) {
//            mediaPlayer.release()
//        }
//    }
//}