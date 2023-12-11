package com.eraqi.chatsdk.ui

import android.net.TrafficStats
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.os.StrictMode.VmPolicy
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.eraqi.chatsdk.R
import com.eraqi.chatsdk.databinding.ActivityMainBinding


class MainActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableStrictMode()
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

    }
    private fun enableStrictMode() {
        val threadPolicy = ThreadPolicy.Builder()
            .detectAll() // Detect all violations
            .penaltyLog() // Log violations to the system log
            .build()
        StrictMode.setThreadPolicy(threadPolicy)
        val vmPolicy = VmPolicy.Builder()
            .detectAll()
            .penaltyLog()
            .build()
        TrafficStats.setThreadStatsTag(1)
        StrictMode.setVmPolicy(vmPolicy)
    }
}
