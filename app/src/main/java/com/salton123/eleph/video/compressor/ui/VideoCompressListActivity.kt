package com.salton123.eleph.video.compressor.ui

import android.os.Bundle
import android.widget.TextView
import com.salton123.base.DelegateActivity
import com.salton123.base.feature.ImmersionFeature
import com.salton123.eleph.R

/**
 * Time:2022/2/2 5:01 下午
 * Author:
 * Description:
 */
class VideoCompressListActivity : DelegateActivity() {
    override fun getLayout(): Int = R.layout.activity_video_compress
    private lateinit var mImmersionFeature: ImmersionFeature
    private lateinit var tvBack: TextView
    private lateinit var tvMore: TextView
    override fun initVariable(savedInstanceState: Bundle?) {
        mImmersionFeature = ImmersionFeature(this)
        addFeature(mImmersionFeature)
    }

    open fun toMoreAction() {
    }

    override fun initViewAndData() {
        tvBack = findViewById(R.id.tvBack)
        tvMore = findViewById(R.id.tvMore)
        tvBack.setOnClickListener {
            finish()
        }
        tvMore.setOnClickListener {
            toMoreAction()
        }
    }
}