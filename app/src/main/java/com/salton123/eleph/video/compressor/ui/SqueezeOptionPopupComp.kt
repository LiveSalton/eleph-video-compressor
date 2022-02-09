package com.salton123.eleph.video.compressor.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import android.widget.TextView
import com.salton123.base.BaseDialogFragment
import com.salton123.eleph.R
import com.salton123.eleph.video.compressor.adapter.RecyclerContentAdapter
import com.salton123.eleph.video.compressor.model.VideoItem
import com.salton123.util.ScreenUtils

/**
 * Time:2022/2/3 10:08 下午
 * Author:
 * Description:
 */
class SqueezeOptionPopupComp : BaseDialogFragment() {
    override fun getLayout(): Int {
        return R.layout.fragment_squeeze_option
    }

    override fun initVariable(savedInstanceState: Bundle?) {
        setStyle(STYLE_NORMAL, R.style.GeneralDialog)
    }

    override fun initViewAndData() {
        val videoItem = arguments.getSerializable("videoItem") as VideoItem?
//        videoItem?.apply {
//            f<TextView>(R.id.tvTitleContent).text = name
//        }
    }

    private lateinit var attachAdapter: RecyclerContentAdapter
    fun attachAdapter(adapter: RecyclerContentAdapter) {
        this.attachAdapter = adapter
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
        window.setBackgroundDrawable(ColorDrawable(Color.WHITE))
    }
}