package mediaSession
//
//import android.annotation.SuppressLint
//import android.content.ComponentName
//import android.media.session.PlaybackState.STATE_STOPPED
//import android.os.Bundle
//import android.os.RemoteException
//import android.support.v4.media.MediaBrowserCompat
//import android.support.v4.media.session.MediaControllerCompat
//import android.support.v4.media.session.PlaybackStateCompat
//import android.view.View
//import android.widget.ImageButton
//import androidx.appcompat.app.AppCompatActivity
//
//class MainActivity : AppCompatActivity() {
////    private lateinit var buttonPlay: ImageButton
//   private lateinit var buttonStop: ImageButton
//    private val STATE_PAUSED = 0
//    private val STATE_PLAYING = 1
//    private val STATE_STOP = -1
//    private var mCurrentState = STATE_PAUSED
//    private lateinit var mMediaBrowserCompat: MediaBrowserCompat
//    private lateinit var mMediaControllerCompat: MediaControllerCompat
//    private lateinit var mPlayPauseToggleButton:ImageButton
//
//    private val mMediaBrowserCompatConnectionCallback = object:MediaBrowserCompat.ConnectionCallback() {
//        override fun onConnected() {
//            super.onConnected()
//            try
//            {
//
//                mMediaBrowserCompat.sessionToken.also { token ->
//
//                    // Create a MediaControllerCompat
//                    val mediaController = MediaControllerCompat(
//                        this@MainActivity, // Context
//                        token
//                    )
//
//                    // Save the controller
//                    MediaControllerCompat.setMediaController(this@MainActivity, mediaController)
//                }
//
//                // Finish building the UI
//               buildTransportControls()
//
//
//                mMediaControllerCompat = MediaControllerCompat(
//                    this@MainActivity,
//                    mMediaBrowserCompat.getSessionToken()
//                )
//                mMediaControllerCompat.registerCallback(mMediaControllerCompatCallback)
//                setMediaController(mediaController)
//                getMediaController().getTransportControls().playFromMediaId(
//                    Integer.toString(R.raw.mittar),
//                    null
//                )
//            }
//            catch (e: RemoteException) {
//            }
//        }
//    }
//    fun buildTransportControls() {
//        val mediaController = MediaControllerCompat.getMediaController(this@MainActivity)
//        // Grab the view for the play/pause button
//        buttonStop = findViewById<ImageButton>(R.id.imageButton2).apply {
//            setOnClickListener {
//                // Since this is a play/pause button, you'll need to test the current state
//                // and choose the action accordingly
//
//                val pbState = mediaController.playbackState.state
//                if (pbState == PlaybackStateCompat.STATE_PLAYING) {
//                    mediaController.transportControls.pause()
//                } else {
//                    mediaController.transportControls.play()
//                }
//            }
//        }
//    }
//
//    private val mMediaControllerCompatCallback = object:MediaControllerCompat.Callback() {
//        override fun onPlaybackStateChanged(state: PlaybackStateCompat) {
//            super.onPlaybackStateChanged(state)
//            if (state == null)
//            {
//                return
//            }
//            when (state.state) {
//                PlaybackStateCompat.STATE_PLAYING -> {
//                    mCurrentState = STATE_PLAYING
//                }
//                PlaybackStateCompat.STATE_PAUSED -> {
//                    mCurrentState = STATE_PAUSED
//                }
//                PlaybackStateCompat.STATE_STOPPED -> {
//                    mCurrentState = STATE_STOPPED
//                }
//            }
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activitmain)
//
//
//        mPlayPauseToggleButton = findViewById(R.id.imageButton)
//        mMediaBrowserCompat = MediaBrowserCompat(
//            this, ComponentName(this, MusicService::class.java),
//            mMediaBrowserCompatConnectionCallback, null
//        )
//        mMediaBrowserCompat.connect()
//        mPlayPauseToggleButton.setOnClickListener(object : View.OnClickListener {
//            @SuppressLint("WrongConstant")
//            override fun onClick(view: View) {
//                if (mCurrentState == STATE_PAUSED) {
//                    mediaController.transportControls.play()
//                    mCurrentState = STATE_PLAYING
//                } else {
//                    if (
//
//                        mediaController.playbackState
//                            !!.state == PlaybackStateCompat.STATE_PLAYING
//                    ) {
//                        getMediaController().getTransportControls().pause()
//                    }
//                    mCurrentState = STATE_PAUSED
//                }
//            }
//        })
//
//    }
//
//    @SuppressLint("WrongConstant")
//    override fun onDestroy() {
//        super.onDestroy()
////        playbackStateCompat = mediaControllerCompat.getPlaybackState()
//        if (mediaController.playbackState
//                !!.getState() == PlaybackStateCompat.STATE_PLAYING
//        ) {
//            mediaController.transportControls.pause()
//        }
//        mMediaBrowserCompat.disconnect()
//    }
//
//
//
//
//}