package com.salton123.service

import android.app.Service
import android.content.Intent
import android.util.Log
import com.salton123.utils.ForegroundNotificationHelper

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/5/25 12:01
 * ModifyTime: 12:01
 * Description:
 */
abstract class AbsService : Service() {
    val helper by lazy { ForegroundNotificationHelper() }

    companion object {
        val TAG = "ProjectionService"
    }

    override fun onCreate() {
        Log.i(TAG, "[onCreate]")
        super.onCreate()
    }

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
        Log.i(TAG, "[onStart]")
        helper.startForegroundNotification(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "[onStartCommand]")
        helper.startForegroundNotification(this)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.i(TAG, "onUnbind")
        helper.deleteForegroundNotification(this)
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "[onDestroy]")
        helper.deleteForegroundNotification(this)
    }
}