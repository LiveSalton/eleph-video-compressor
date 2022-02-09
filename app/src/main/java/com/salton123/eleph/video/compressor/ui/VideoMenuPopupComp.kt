package com.salton123.eleph.video.compressor.ui

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import android.widget.LinearLayout
import com.salton123.base.BaseDialogFragment
import com.salton123.eleph.R
import com.salton123.eleph.video.compressor.adapter.RecyclerContentAdapter
import com.salton123.eleph.video.compressor.model.VideoItem
import com.salton123.eleph.video.compressor.persistence.VideoDao
import com.salton123.eleph.video.compressor.task.MediaFileScanTask
import com.salton123.eleph.video.compressor.ui.dialog.RenameDialog
import com.salton123.eleph.video.kt.toast
import com.salton123.manager.ActivityLifeCycleManager
import com.salton123.util.ScreenUtils
import java.io.File

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

    private lateinit var videoItem: VideoItem
    private lateinit var attachAdapter: RecyclerContentAdapter
    fun attachAdapter(adapter: RecyclerContentAdapter) {
        this.attachAdapter = adapter
    }

    override fun initViewAndData() {
        videoItem = arguments.getSerializable("videoItem") as VideoItem
        if (videoItem == null) {
            dismissAllowingStateLoss()
        }
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

        f<LinearLayout>(R.id.llRename).setOnClickListener {
            ActivityLifeCycleManager.INSTANCE.currentResumedActivity.let {
                RenameDialog(it, videoItem, attachAdapter).show()
            }
            dismissAllowingStateLoss()
        }
        f<LinearLayout>(R.id.llDelete).setOnClickListener {
            ActivityLifeCycleManager.INSTANCE.currentResumedActivity.let { aty ->
                AlertDialog.Builder(aty)
                    .setMessage(getString(R.string.delete_tips))
                    .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                        try {
                            val file = File(videoItem.filePath)
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