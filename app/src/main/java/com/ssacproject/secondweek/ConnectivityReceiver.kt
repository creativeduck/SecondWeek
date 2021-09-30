package com.ssacproject.secondweek

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ConnectivityReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val manager = context.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE)
                        as ConnectivityManager
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            val activeNetwork = manager.activeNetwork
            val networkCapabilities = manager.getNetworkCapabilities(activeNetwork)

            if (networkCapabilities != null &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                showToast(context, "connected")
            } else {
                showToast(context, "not connected")
            }
        }
    }

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}