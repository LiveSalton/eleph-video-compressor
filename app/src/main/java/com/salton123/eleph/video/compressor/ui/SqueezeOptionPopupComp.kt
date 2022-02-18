package com.salton123.eleph.video.compressor.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
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
import com.salton123.eleph.video.compressor.observe.Observable
import com.salton123.eleph.video.compressor.persistence.VideoDao
import com.salton123.eleph.video.compressor.task.MediaFileScanTask
import com.salton123.eleph.video.compressor.ui.dialog.RenameDialog
import com.salton123.manager.ActivityLifeCycleManager
import com.salton123.utils.ScreenUtils
import kt.toast
import java.io.File

/**
 * Time:2022/2/3 10:08 下午
 * Author:
 * Description:
 */
class SqueezeOptionPopupComp : BaseDialogFragment(), Observable<VideoItem> {
    override fun getLayout(): Int {
        return R.layout.fragment_squeeze_option
    }

    override fun initVariable(savedInstanceState: Bundle?) {
        setStyle(STYLE_NORMAL, R.style.GeneralDialog)
    }

    private lateinit var rgCoder: RadioGroup
    private lateinit var rgDensity: RadioGroup
    private lateinit var rbOrigin: RadioButton
    private lateinit var rbThreeQuarters: RadioButton
    private lateinit var rbHalf: RadioButton
    private lateinit var tvOriginName: TextView
    private lateinit var tvOriginPlay: TextView
    private lateinit var tvOriginInfo: TextView
    private lateinit var tvOriginShowInfo: TextView
    private lateinit var tvOriginDelete: TextView
    private lateinit var tvOriginRename: TextView
    var encoder = "h264"
    var density: Pair<String, String>? = Pair("", "iw:ih")

    @SuppressLint("SetTextI18n")
    override fun initViewAndData() {
        val videoItem = arguments.getSerializable("videoItem") as VideoItem?
        videoItem?.apply {
            val position = arguments.getInt("position")
            density = originDensity()
            rgCoder = f(R.id.rgCoder)
            tvOriginName = f(R.id.tvOriginName)
            tvOriginPlay = f(R.id.tvOriginPlay)
            tvOriginInfo = f(R.id.tvOriginInfo)
            tvOriginShowInfo = f(R.id.tvOriginShowInfo)
            tvOriginDelete = f(R.id.tvOriginDelete)
            tvOriginRename = f(R.id.tvOriginRename)
            tvOriginName.text = name
            tvOriginPlay.setOnClickListener {
                startActivity(Intent(activity(), VideoPlayActivity::class.java).apply {
                    putExtra("videoItem", videoItem)
                })
                dismissAllowingStateLoss()
            }
            tvOriginShowInfo.setOnClickListener {
                ActivityLifeCycleManager.INSTANCE.currentResumedActivity.let { aty ->
                    VideoInfoPopupComp().apply {
                        arguments = Bundle().apply {
                            putSerializable("videoItem", videoItem)
                        }
                        show(aty.fragmentManager, "VideoInfoPopupComp")
                    }
                }
                dismissAllowingStateLoss()
            }
            tvOriginDelete.setOnClickListener {
                ActivityLifeCycleManager.INSTANCE.currentResumedActivity.let { aty ->
                    AlertDialog.Builder(aty)
                        .setMessage(getString(R.string.delete_tips))
                        .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                            dialog.dismiss()
                        }
                        .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                            try {
                                val file = File(filePath)
                                val ret = file.delete()
                                VideoDao.deleteVideo(videoItem)
                                MediaFileScanTask.removeVideoItem(videoItem)
                                if (ret || !file.exists()) {
                                    R.string.video_delete_success.toast()
                                    attachAdapter.notifyItemDelete(videoItem)
                                } else {
                                    R.string.video_delete_failed.toast()
                                }
                            } catch (ex: Exception) {
                                ex.printStackTrace()
                            }
                            dialog.dismiss()
                        }
                        .create().show()
                }
            }
            tvOriginRename.setOnClickListener {
                ActivityLifeCycleManager.INSTANCE.currentResumedActivity.let {
                    RenameDialog(it, videoItem) { item ->
                        observe(item)
                    }.show()
//                    VideoItemObserver.add(this@SqueezeOptionPopupComp)
                }
                dismissAllowingStateLoss()
            }
            tvOriginInfo.text = "${originDensity().first} | ${sizeOfStr()} | " +
                "${mimeType} | ${durationOfStr()}"
            rgCoder.setOnCheckedChangeListener { _, checkedId ->
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
                        halfDensity()
                    }
                    R.id.rbThreeQuarters -> {
                        threeQuarterDensity()
                    }
                    else -> {
                        originDensity()
                    }
                }
            }
            rbOrigin = f(R.id.rbOrigin)
            rbThreeQuarters = f(R.id.rbThreeQuarters)
            rbHalf = f(R.id.rbHalf)
            rbOrigin.text = originDensity().first
            rbThreeQuarters.text = threeQuarterDensity().first
            rbHalf.text = halfDensity().first
            f<TextView>(R.id.tvCancel).setOnClickListener {
                dismissAllowingStateLoss()
            }
            f<TextView>(R.id.tvSubmit).setOnClickListener {
                rgCoder.checkedRadioButtonId
                val prop = SqueezeProp(filePath, encoder, "aac", density ?: Pair("", "iw:ih"))
                attachAdapter.startSqueeze(videoItem, position, prop)
                dismissAllowingStateLoss()
            }
        } ?: kotlin.run { dismissAllowingStateLoss() }
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
    }

    override fun onDestroy() {
        super.onDestroy()
//        VideoItemObserver.remove(this)
    }

    override fun observe(data: VideoItem) {
        tvOriginName.text = data.name
//        VideoItemObserver.remove(this)
    }
}
