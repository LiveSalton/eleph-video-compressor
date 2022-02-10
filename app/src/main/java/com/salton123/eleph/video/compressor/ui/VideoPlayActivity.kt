package com.salton123.eleph.video.compressor.ui

import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import com.salton123.base.DelegateActivity
import com.salton123.base.feature.ImmersionFeature
import com.salton123.eleph.R
import com.salton123.eleph.video.compressor.model.VideoItem
import com.salton123.video.player.VideoBean
import com.salton123.video.player.VideoViewWrapper

/**
 * Time:2022/2/10 17:43
 * Author:
 * Description:
 */
class VideoPlayActivity : DelegateActivity() {
    override fun getLayout(): Int = R.layout.activity_video_play

    private lateinit var videoPlayer: VideoViewWrapper
    private lateinit var tvTitle: TextView
    private lateinit var tvBack: TextView
    private lateinit var mImmersionFeature: ImmersionFeature
    override fun initVariable(savedInstanceState: Bundle?) {
        mImmersionFeature = ImmersionFeature(this)
        addFeature(mImmersionFeature)
    }

    private var videoItem: VideoItem? = null
    private var isPlaySqueeze = false
    override fun initViewAndData() {
        videoPlayer = f(R.id.videoPlayer)
        tvBack = f(R.id.tvBack)
        tvTitle = f(R.id.tvTitle)
        tvBack.setOnClickListener {
            finish()
        }

        videoItem = intent?.getSerializableExtra("videoItem") as VideoItem?
        isPlaySqueeze = intent?.getBooleanExtra("isPlaySqueeze", false) ?: false
        videoItem?.apply {
            tvTitle.text = name
            if (isPlaySqueeze) {
                videoPlayer.updatePlayUrl(VideoBean(0, Uri.parse(squeezeSavePath)))
            } else {
                videoPlayer.updatePlayUrl(VideoBean(0, Uri.parse(filePath)))
            }

            videoPlayer.startPlay()
        }
    }

}