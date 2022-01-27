package com.salton123.eleph.video.compressor.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.salton123.base.feature.ImmersionFeature

/**
 * Time:2022/1/27 10:52
 * Author:
 * Description:
 */
class SplashActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        ImmersionFeature(this).onBind()
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onBackPressed() {
    }
}