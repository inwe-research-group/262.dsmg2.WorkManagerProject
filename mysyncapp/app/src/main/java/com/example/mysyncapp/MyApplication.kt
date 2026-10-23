package com.example.mysyncapp

import android.app.Application
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.work.Configuration
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.mysyncapp.worker.SyncWorker

class MyApplication : Application(), Configuration.Provider {
    override fun onCreate() {
        super.onCreate()
        // Optional: register network callback to enqueue sync when network becomes available
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val nr = NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()
        cm.registerNetworkCallback(nr, object: ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                val work = OneTimeWorkRequestBuilder<SyncWorker>()
                    .setConstraints(SyncWorker.buildConstraints())
                    .build()
                WorkManager.getInstance(applicationContext)
                    .enqueueUniqueWork("sync_on_connect", ExistingWorkPolicy.KEEP, work)
            }
        })
    }
    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()
    }
}
