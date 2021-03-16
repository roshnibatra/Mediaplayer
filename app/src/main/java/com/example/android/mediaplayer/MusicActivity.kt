package com.example.android.mediaplayer

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MusicActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var buttonPlay: ImageButton
    private lateinit var buttonStop: ImageButton
    private lateinit var pauseButton: ImageButton
    private var isPlaying: Boolean = true
    private var musicNotificationManager: MusicNotificationManager? = null
    private var musicForeground : MusicForeground? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_music)

        buttonPlay = findViewById(R.id.imageButton)
        buttonStop = findViewById(R.id.imageButton2)
        pauseButton = findViewById(R.id.pauseButton)

        buttonPlay.setOnClickListener(this@MusicActivity)
        buttonStop.setOnClickListener(this@MusicActivity)
        pauseButton.setOnClickListener(this@MusicActivity)
    }

    override fun onClick(v: View?) {
        if (v == buttonPlay) {
            if (isPlaying) {
                val action = "PLAY"
                buttonPlay.setImageResource(R.drawable.pause)
                val myService = Intent(this@MusicActivity, MusicForeground::class.java)
                myService.action = action
                startService(myService)
            }

        } else if (v == buttonStop) {
            isPlaying = true
            buttonPlay.setImageResource(R.drawable.play)
            stopService(Intent(this@MusicActivity, MusicForeground::class.java))
        } else if (v == pauseButton) {
            buttonPlay.setImageResource(R.drawable.play)
            val action = "PAUSE"
            val myService =
                Intent(this@MusicActivity, MusicForeground::class.java)
            myService.action = action
            startService(myService)
        }



    }


}