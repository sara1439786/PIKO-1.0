package com.example.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Facebook leads are received securely by the MilesWeb webhook.
 * The Android app then pulls them through the authenticated CRM API.
 * This service remains only so the original UI controls do not crash.
 */
class FacebookLeadPollingService : Service() {
    companion object {
        private val _isServiceRunning = MutableStateFlow(false)
        val isServiceRunning = _isServiceRunning.asStateFlow()
        private val _lastPollTimestamp = MutableStateFlow(0L)
        val lastPollTimestamp = _lastPollTimestamp.asStateFlow()
        private val _leadsPolledTotal = MutableStateFlow(0)
        val leadsPolledTotal = _leadsPolledTotal.asStateFlow()
        private val _recentLogs = MutableStateFlow<List<String>>(listOf("Facebook leads are handled by the secure MilesWeb webhook."))
        val recentLogs = _recentLogs.asStateFlow()
    }

    override fun onCreate() {
        super.onCreate()
        _isServiceRunning.value = true
        _lastPollTimestamp.value = System.currentTimeMillis()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        _recentLogs.value = listOf("Server webhook mode active. New Meta leads sync through crm.rscc.in.")
        stopSelf()
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        _isServiceRunning.value = false
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
