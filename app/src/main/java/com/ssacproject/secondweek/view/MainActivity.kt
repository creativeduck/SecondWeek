package com.ssacproject.secondweek.view

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.ssacproject.secondweek.R

class MainActivity : AppCompatActivity() {
    lateinit var video: VideoView
    lateinit var textView: TextView
    lateinit var btnGo: Button
    lateinit var btnBack: Button
    var position: Int = 0
    // 액티비티가 만들어질 때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState!=null) {
            position = Integer.parseInt(savedInstanceState.getString("position"))
        }
        setContentView(R.layout.activity_main)
        Toast.makeText(this, "onCreate 호출", Toast.LENGTH_SHORT).show()
        video = findViewById(R.id.video)
        video.setMediaController(MediaController(this))
        video.requestFocus()

        textView = findViewById(R.id.textView)
        btnBack = findViewById(R.id.btnBack)
        btnBack.setOnClickListener(View.OnClickListener {
            var str: String = video.currentPosition.toString()
            textView.setText(str)
        })

        btnGo = findViewById(R.id.btnGo)
        btnGo.setOnClickListener(View.OnClickListener {
            video.seekTo(Integer.parseInt(textView.text.toString()))
        })


    }
    // 액티비티가 다시 전면에 보임
    override fun onRestart() {
        super.onRestart()
        Toast.makeText(this, "onRestart 호출", Toast.LENGTH_SHORT).show()
    }
    // 액티비티가 시작할 때
    override fun onStart() {
        super.onStart()
        Toast.makeText(this, "onStart 호출", Toast.LENGTH_SHORT).show()
        video.setVideoURI(Uri.parse("https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4"))


    }
    // 액티비티가 재개될 때
    override fun onResume() {
        super.onResume()
        Toast.makeText(this, "onResume 호출", Toast.LENGTH_SHORT).show()
        if (position!=0) {
            video.seekTo(position)
        }
        else {
            video.start()
        }

    }
    // 액티비티가 일시정지할 때. 사용자가 액티비티에서 떠날 때
    override fun onPause() {
        super.onPause()
        Toast.makeText(this, "onPause 호출", Toast.LENGTH_SHORT).show()
        // 단순히 일시정지
        textView.setText(video.currentPosition.toString())
        video.pause()
    }
    // 액티비티가 더 이상 사용자에게 표시되지 않을 때
    override fun onStop() {
        super.onStop()
        Toast.makeText(this, "onStop 호출", Toast.LENGTH_SHORT).show()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        var str:String = textView.text.toString()
        outState.putString("position", str)
    }


    // 액티비티가 소멸되기 직전에
    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "onDestroy 호출", Toast.LENGTH_SHORT).show()
    }
}