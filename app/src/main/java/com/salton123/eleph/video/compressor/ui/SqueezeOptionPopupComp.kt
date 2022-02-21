package com.salton123.eleph.video.compressor.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
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
import com.salton123.eleph.video.compressor.utils.Utils
import com.salton123.manager.ActivityLifeCycleManager
import com.salton123.utils.ScreenUtils
import kt.executeByIo
import kt.runOnUi
import kt.toast
import java.io.File

/**
 * Time:2022/2/3 10:08 下午
 * Author:
 * Description:
 */
@SuppressLint("SetTextI18n")
class SqueezeOptionPopupComp : BaseDialogFragment(), Observable<VideoItem> {
    var encoder = "h264"
    var density: Pair<String, String>? = Pair("", "iw:ih")

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

    private lateinit var tvSqueezedName: TextView
    private lateinit var tvSqueezedShowInfo: TextView
    private lateinit var tvSqueezedInfo: TextView
    private lateinit var tvSqueezedDelete: TextView
    private lateinit var tvSqueezedPlay: TextView
    private lateinit var llSqueezedFile: LinearLayout

    private fun initView() {
        rgCoder = f(R.id.rgCoder)
        tvOriginName = f(R.id.tvOriginName)
        tvOriginPlay = f(R.id.tvOriginPlay)
        tvOriginInfo = f(R.id.tvOriginInfo)
        tvOriginShowInfo = f(R.id.tvOriginShowInfo)
        tvOriginDelete = f(R.id.tvOriginDelete)
        tvOriginRename = f(R.id.tvOriginRename)
        rgDensity = f(R.id.rgDensity)
        rbOrigin = f(R.id.rbOrigin)
        rbThreeQuarters = f(R.id.rbThreeQuarters)
        rbHalf = f(R.id.rbHalf)
        tvSqueezedName = f(R.id.tvSqueezedName)
        tvSqueezedShowInfo = f(R.id.tvSqueezedShowInfo)
        tvSqueezedInfo = f(R.id.tvSqueezedInfo)
        tvSqueezedDelete = f(R.id.tvSqueezedDelete)
        tvSqueezedPlay = f(R.id.tvSqueezedPlay)
        llSqueezedFile = f(R.id.llSqueezedFile)
    }

    override fun initViewAndData() {
        initView()
        var videoItem = arguments.getSerializable("videoItem") as VideoItem?
        videoItem?.apply {
            val position = arguments.getInt("position")
            density = originDensity()
            rbOrigin.text = originDensity().first
            rbThreeQuarters.text = threeQuarterDensity().first
            rbHalf.text = halfDensity().first
            updateSqueezedInfo(squeezeSavePath)
            updateOriginInfo(this)

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

    private fun updateOriginInfo(videoItem: VideoItem) {
        videoItem.apply {
            tvOriginInfo.text = "${originDensity().first} | ${sizeOfStr()} | " +
                "$mimeType | ${durationOfStr()}"
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
                dismissAllowingStateLoss()
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
        }

    }

    private fun updateSqueezedFileLayout(squeezeSavePath: String): Boolean {
        return if (File(squeezeSavePath).exists()) {
            llSqueezedFile.visibility = View.VISIBLE
            true
        } else {
            llSqueezedFile.visibility = View.GONE
            false
        }
    }

    private fun updateSqueezedInfo(squeezeSavePath: String) {
        if (!updateSqueezedFileLayout(squeezeSavePath)) {
            return
        }
        val videoItem = Utils.retrieveFile(File(squeezeSavePath))
        videoItem.apply {
            tvSqueezedName.text = videoItem.name
            tvSqueezedInfo.text = "${originDensity().first} | ${sizeOfStr()} | " +
                "$mimeType | ${durationOfStr()}"

            tvSqueezedPlay.setOnClickListener {
                startActivity(Intent(activity(), VideoPlayActivity::class.java).apply {
                    putExtra("videoItem", videoItem)
                })
                dismissAllowingStateLoss()
            }
            tvSqueezedDelete.setOnClickListener {
                ActivityLifeCycleManager.INSTANCE.currentResumedActivity.let { aty ->
                    AlertDialog.Builder(aty)
                        .setMessage(getString(R.string.delete_tips))
                        .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                            dialog.dismiss()
                        }
                        .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                            try {
                                val file = File(squeezeSavePath)
                                val ret = file.delete()
                                if (ret || !file.exists()) {
                                    updateSqueezedFileLayout(squeezeSavePath)
                                    R.string.video_delete_success.toast()
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
            tvSqueezedShowInfo.setOnClickListener {
                ActivityLifeCycleManager.INSTANCE.currentResumedActivity.let { aty ->
                    executeByIo {
                        VideoInfoPopupComp().apply {

                            arguments = Bundle().apply {
                                putSerializable("videoItem", videoItem)
                            }
                            runOnUi {
                                show(aty.fragmentManager, "VideoInfoPopupComp")
                            }
                        }
                    }
                }
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
        dialog.window?.let { window ->
            ScreenUtils.hideNavigationBar(window)
            val params = window.attributes
            params.gravity = Gravity.BOTTOM
            params.width = WindowManager.LayoutParams.MATCH_PARENT
            window.attributes = params
            window.setWindowAnimations(R.style.slide_popup_ani)
        }
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
