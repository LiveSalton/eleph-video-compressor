package com.salton123.service

import android.app.PendingIntent
import android.content.Intent
import android.os.IBinder
import com.salton123.eleph.video.compressor.ui.HomeActivity

class SqueezeService : AbsService() {
    override fun onCreate() {
        super.onCreate()
        val intent = Intent(this, HomeActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 11, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        helper.pendingIntent = pendingIntent
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onUnbind(intent: Intent?): Boolean {
        super.onUnbind(intent)
        return true
    }
}