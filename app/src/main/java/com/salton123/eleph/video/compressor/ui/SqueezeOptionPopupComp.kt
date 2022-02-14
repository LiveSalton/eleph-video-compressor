package com.salton123.eleph.video.compressor.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import com.salton123.base.BaseDialogFragment
import com.salton123.eleph.R
import com.salton123.eleph.video.compressor.adapter.RecyclerContentAdapter
import com.salton123.eleph.video.compressor.model.SqueezeProp
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

    private lateinit var rgEncoder: RadioGroup
    private lateinit var rgDensity: RadioGroup
    private lateinit var rbOrigin: RadioButton
    private lateinit var rbThreeQuarters: RadioButton
    private lateinit var rbHalf: RadioButton
    var encoder = "h264"
    var density = ""
    override fun initViewAndData() {
        val videoItem = arguments.getSerializable("videoItem") as VideoItem?
        val position = arguments.getInt("position")
        density = videoItem?.originDensity() ?: ""
        rgEncoder = f(R.id.rgEncoder)
        rgEncoder.setOnCheckedChangeListener { _, checkedId ->
            encoder = when (checkedId) {
                R.id.rbH265 -> {
                    "hevc"
                }
                R.id.rbMpeg4 -> {
                    "mpeg4"
                }
                else -> {
                    "h264"
                }
            }
        }
        rgDensity = f(R.id.rgDensity)
        rgDensity.setOnCheckedChangeListener { _, checkedId ->
            density = when (checkedId) {
                R.id.rbHalf -> {
                    videoItem?.halfDensity() ?: ""
                }
                R.id.rbThreeQuarters -> {
                    videoItem?.threeQuarterDensity() ?: ""
                }
                else -> {
                    videoItem?.originDensity() ?: ""
                }
            }
        }
        rbOrigin = f(R.id.rbOrigin)
        rbThreeQuarters = f(R.id.rbThreeQuarters)
        rbHalf = f(R.id.rbHalf)
        rbOrigin.text = videoItem?.originDensity()
        rbThreeQuarters.text = videoItem?.threeQuarterDensity()
        rbHalf.text = videoItem?.halfDensity()
        videoItem?.apply {
            f<TextView>(R.id.tvCancel).setOnClickListener {
                dismiss()
            }
            f<TextView>(R.id.tvSubmit).setOnClickListener {
                rgEncoder.checkedRadioButtonId
                val prop = SqueezeProp(videoItem.filePath, encoder, "aac", density)
                attachAdapter?.startSqueeze(videoItem, position, prop)
                dismissAllowingStateLoss()
            }
        }
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
