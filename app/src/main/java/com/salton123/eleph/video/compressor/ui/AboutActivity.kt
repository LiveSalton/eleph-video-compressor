package com.salton123.eleph.video.compressor.ui

import android.widget.ImageButton
import android.widget.TextView
import com.salton123.base.BaseActivity
import com.salton123.eleph.BuildConfig
import com.salton123.eleph.R

/**
 * User: wujinsheng1@yy.com
 * Date: 2020/4/22 21:31
 * ModifyTime: 21:31
 * Description:
 */
class AboutActivity : BaseActivity() {
    private lateinit var tvVersion: TextView
    private lateinit var tvBuildHash: TextView
    private lateinit var tvBuildTime: TextView
    private lateinit var btnClose: ImageButton
    override fun initViewAndData() {
        super.initViewAndData()
        tvVersion = f(R.id.tvVersion)
        tvBuildHash = f(R.id.tvBuildHash)
        tvBuildTime = f(R.id.tvBuildTime)
        btnClose = f(R.id.btnClose)
        tvTitle.text = getString(R.string.setting_about)
        tvVersion.text = BuildConfig.VERSION_NAME
        tvBuildHash.text = BuildConfig.GIT_HASH
        tvBuildTime.text = BuildConfig.BUILD_TIME
        btnClose.setOnClickListener {
            finish()
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_about
}