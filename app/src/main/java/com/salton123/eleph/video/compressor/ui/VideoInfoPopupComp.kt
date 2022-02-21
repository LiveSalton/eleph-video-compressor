package com.salton123.eleph.video.compressor.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.arthenica.ffmpegkit.MediaInformationSession
import com.salton123.base.BaseDialogFragment
import com.salton123.eleph.R
import com.salton123.eleph.video.compressor.model.VideoItem
import com.salton123.eleph.video.compressor.task.FFmpegCompressor
import com.salton123.utils.ScreenUtils
import com.salton123.view.IconFontTextView
import kt.executeByCached
import kt.runOnUi
import java.text.SimpleDateFormat
import java.util.*

/**
 * User: newSalton@outlook.com
 * Date: 2019/2/19 14:20
 * ModifyTime: 14:20
 * Description:
 */
class VideoInfoPopupComp : BaseDialogFragment() {
    override fun getLayout(): Int {
        return R.layout.fragment_video_info
    }

    override fun initVariable(savedInstanceState: Bundle?) {
        setStyle(STYLE_NORMAL, R.style.GeneralDialog)
    }

    var isOpenMore = false
    var session: MediaInformationSession? = null
    private var videoItem: VideoItem? = null
    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    override fun initViewAndData() {
        videoItem = arguments?.getSerializable("videoItem") as VideoItem?
        videoItem?.apply {
            f<TextView>(R.id.tvTitleContent).text = name
            f<TextView>(R.id.tvResolutionContent).text = "${width}x${height}"
            f<TextView>(R.id.tvSizeContent).text = sizeOfStr()
            f<TextView>(R.id.tvFormatContent).text = mimeType
            f<TextView>(R.id.tvPathContent).text = filePath
            f<TextView>(R.id.tvDurationContent).text = durationOfStr()
            f<TextView>(R.id.tvDateContent).text = simpleDateFormat.format(createdAt)
            f<TextView>(R.id.tvClose).setOnClickListener {
                dismissAllowingStateLoss()
            }
        }

        val llMoreContent = f<LinearLayout>(R.id.llMoreContent)
        val tvMoreArrow = f<IconFontTextView>(R.id.tvMoreArrow)
        f<LinearLayout>(R.id.llMore)
            .setOnClickListener {
                if (!isOpenMore) {
                    isOpenMore = true
                    llMoreContent.visibility = View.VISIBLE
                    tvMoreArrow.text = getString(R.string.if_arrow_down)
                    loadMoreInfo()
                } else {
                    llMoreContent.visibility = View.GONE
                    tvMoreArrow.text = getString(R.string.if_arrow_right)
                    isOpenMore = false
                }
            }
    }

    private fun loadMoreInfo() {
        executeByCached {
            if (session == null) {
                session = FFmpegCompressor.getVideoInfo(videoItem?.filePath)
            }
            session?.mediaInformation?.streams?.forEach { streamInfo ->
                if ("video" == streamInfo.allProperties.optString("codec_type")) {
                    val totalFrames = streamInfo.allProperties.optString("nb_frames").toString()
                    val codec_name = streamInfo.allProperties.optString("codec_name").toString()
                    val profile = streamInfo.allProperties.optString("profile").toString()
                    val bit_rate = streamInfo.allProperties.optString("bit_rate").toString()
                    val r_frame_rate = streamInfo.allProperties.optString("r_frame_rate").toString()
                    runOnUi {
                        f<TextView>(R.id.tvVideoTypeContent).text = codec_name
                        f<TextView>(R.id.tvVideoProfileContent).text = profile
                        f<TextView>(R.id.tvVideoResolutionContent).text = r_frame_rate
                        f<TextView>(R.id.tvVideoBitrateContent).text = bit_rate
                        f<TextView>(R.id.tvVideoTotalFrameContent).text = totalFrames
                    }
                } else if ("audio" == streamInfo.allProperties.optString("codec_type")) {
                    val totalFrames = streamInfo.allProperties.optString("nb_frames").toString()
                    val codec_name = streamInfo.allProperties.optString("codec_name").toString()
                    val profile = streamInfo.allProperties.optString("profile").toString()
                    val sample_rate = streamInfo.allProperties.optString("sample_rate").toString()
                    val channel_layout = streamInfo.allProperties.optString("channel_layout").toString()
                    val bit_rate = streamInfo.allProperties.optString("bit_rate").toString()
                    runOnUi {
                        f<TextView>(R.id.tvAudioTypeContent).text = codec_name
                        f<TextView>(R.id.tvAudioProfileContent).text = profile
                        f<TextView>(R.id.tvAudioSampleRateContent).text = sample_rate
                        f<TextView>(R.id.tvAudioChannelContent).text = channel_layout
                        f<TextView>(R.id.tvAudioLanguageContent).text = totalFrames
                        f<TextView>(R.id.tvAudioBitRateContent).text = bit_rate
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog.window?.let { window ->
            ScreenUtils.hideNavigationBar(window)
            val params = window.attributes
            params.gravity = Gravity.CENTER
            val width = ScreenUtils.getScreenWidth() - ScreenUtils.dp2px(60f)
            params.width = width
            window.attributes = params
            window.setWindowAnimations(R.style.slide_popup_ani)
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "[onResume]")
    }

    companion object {
        private const val TAG = "VideoInfoPopupComp"
    }
}