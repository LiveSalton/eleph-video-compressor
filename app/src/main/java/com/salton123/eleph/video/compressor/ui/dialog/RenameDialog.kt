package com.salton123.eleph.video.compressor.ui.dialog

import android.app.AlertDialog
import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.salton123.eleph.R
import com.salton123.eleph.video.compressor.adapter.RecyclerContentAdapter
import com.salton123.eleph.video.compressor.model.VideoItem
import com.salton123.eleph.video.compressor.persistence.VideoDao
import kt.toast
import java.io.File

/**
 * Time:2022/2/5 10:15 上午
 * Author:
 * Description:
 */
class RenameDialog(context: Context, videoItem: VideoItem, attachAdapter: RecyclerContentAdapter)
    : AlertDialog(context, R.style.GeneralDialog) {
    init {
        val view = View.inflate(context, R.layout.dialog_view_rename, null)
        setView(view)
        val etInput = view.findViewById<EditText>(R.id.etInput)
        val pathFile = File(videoItem.filePath)
        val temp = (pathFile.name.substring(0, pathFile.name.lastIndexOf('.')))
        etInput.setText(temp)
        view.findViewById<TextView>(R.id.tvCancel).setOnClickListener {
            dismiss()
        }
        view.findViewById<TextView>(R.id.tvCommit).setOnClickListener {
            val inputText = etInput.text.toString().trim()
            if (!TextUtils.isEmpty(inputText)) {
//                Utils.renameFile(File(videoItem.filePath), inputText)
                val file = File(videoItem.filePath)
                val tempFileName = (inputText + file.name.substring(file.name.lastIndexOf('.')))
                val newFile = File(file.parentFile, tempFileName)
                if (file.renameTo(newFile)) {
                    videoItem.name = tempFileName
                    videoItem.filePath = newFile.absolutePath
                    VideoDao.updateVideo(videoItem)
                    attachAdapter.notifyItemChange(videoItem)
                } else {
                    toast(context.getString(R.string.rename_failed))
                }
                dismiss()
            } else {
                toast(context.getString(R.string.input_a_name))
            }
        }
    }
}