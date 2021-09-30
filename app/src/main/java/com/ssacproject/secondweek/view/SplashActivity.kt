package com.ssacproject.secondweek.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {
    var handler: Handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        handler.postDelayed(Runnable {
//            var intent: Intent = Intent(applicationContext, MainActivity2::class.java)
//            startActivity(intent)
//            finish()
//        }, 1000)

        val backgroundExecutor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
        val mainExecutor: Executor = ContextCompat.getMainExecutor(this)

        backgroundExecutor.schedule({
            mainExecutor.execute {
                val intent = Intent(applicationContext, MainActivity2::class.java)
                startActivity(intent)
                finish()
            }
        }, 1, TimeUnit.SECONDS)
    }
}