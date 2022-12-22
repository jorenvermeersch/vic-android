package com.example.vic.misc

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class Global {
    companion object {

        val isDevelopment: Boolean = false

        fun isOnline(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
        }
    }
}