package com.ssacproject.secondweek.view

import android.app.Activity
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.ssacproject.secondweek.R


class FullScreenActivity : AppCompatActivity() {
    private lateinit var playerView: PlayerView
    private lateinit var simpleExoPlayer: SimpleExoPlayer
    lateinit var exoFullScreen: ImageButton
    lateinit var title: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen)

        playerView = findViewById(R.id.playerView)
        simpleExoPlayer = SimpleExoPlayer.Builder(this).build()
        playerView.player = simpleExoPlayer

        exoFullScreen = playerView.findViewById(R.id.exo_fullscreen)
        exoFullScreen.setOnClickListener {
            finish()
        }
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        val params = playerView.layoutParams as ConstraintLayout.LayoutParams
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.MATCH_PARENT
        playerView.layoutParams = params

        val videoUrl = intent.getStringExtra("url")
        val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
        val userAgent = Util.getUserAgent(this, this.applicationInfo.name)
        val factory = DefaultDataSourceFactory(this, userAgent)
        val progressiveMediaSource = ProgressiveMediaSource.Factory(factory).createMediaSource(mediaItem)

        simpleExoPlayer.setMediaSource(progressiveMediaSource)
        simpleExoPlayer.prepare()
        title = intent.getStringExtra("title")!!
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        var position = getSharedIntData("prefPlay", "position${title}")
        simpleExoPlayer.seekTo(position.toLong())
        simpleExoPlayer.play()
    }

    override fun onPause() {
        super.onPause()
        setSharedData("prefPlay", "position${title}", simpleExoPlayer.currentPosition.toInt())
        simpleExoPlayer.pause()
    }

    override fun onStop() {
        super.onStop()
        simpleExoPlayer.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        simpleExoPlayer.release()
    }

    fun setSharedData(name: String, key: String, data: Int) {
        var pref: SharedPreferences = getSharedPreferences(name, Activity.MODE_PRIVATE)
        var editor: SharedPreferences.Editor = pref.edit()
        editor.putInt(key, data)
        editor.apply()
    }

    fun getSharedIntData(name: String, key: String): Int {
        var pref: SharedPreferences = getSharedPreferences(name, Activity.MODE_PRIVATE)
        return pref.getInt(key, 0)
    }
}