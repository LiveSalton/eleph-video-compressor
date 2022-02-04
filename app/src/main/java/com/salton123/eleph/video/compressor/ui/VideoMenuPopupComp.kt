package com.salton123.eleph.video.compressor.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import android.widget.LinearLayout
import com.salton123.base.BaseDialogFragment
import com.salton123.eleph.R
import com.salton123.eleph.video.compressor.model.VideoItem
import com.salton123.manager.ActivityLifeCycleManager
import com.salton123.util.ScreenUtils

/**
 * Time:2022/2/4 9:34 下午
 * Author:
 * Description:
 */
class VideoMenuPopupComp : BaseDialogFragment() {
    override fun getLayout(): Int {
        return R.layout.fragment_video_menu
    }

    override fun initVariable(savedInstanceState: Bundle?) {
        setStyle(STYLE_NORMAL, R.style.GeneralDialog)
    }

    var videoItem: VideoItem? = null
    override fun initViewAndData() {
        videoItem = arguments.getSerializable("videoItem") as VideoItem?
        f<LinearLayout>(R.id.llPlayNow).setOnClickListener {

        }
        f<LinearLayout>(R.id.llFileInfo).setOnClickListener {
            VideoInfoPopupComp().apply {
                arguments = Bundle().apply {
                    putSerializable("videoItem", videoItem)
                }
                ActivityLifeCycleManager.INSTANCE.currentResumedActivity.fragmentManager?.let {
                    show(it, "VideoInfoPopupComp")
                }
            }
            dismissAllowingStateLoss()
        }
    }

    override fun onStart() {
        super.onStart()
        val window = dialog.window
        ScreenUtils.hideNavigationBar(window)
        val params = window.attributes
        params.gravity = Gravity.BOTTOM
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        window.attributes = params
        window.setWindowAnimations(R.style.slide_popup_ani)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}