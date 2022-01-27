package com.salton123.eleph.video.compressor.adapter

import android.content.Context
import android.media.MediaMetadataRetriever
import android.text.format.Formatter
import android.util.Log
import com.salton123.adapter.abslistview.CommonAdapter
import com.salton123.adapter.abslistview.ViewHolder
import com.salton123.bookmarksbrowser.R
import com.salton123.eleph.video.compressor.model.VideoItem
import com.salton123.eleph.video.compressor.utils.Utils
import org.xutils.x
import java.io.File

/**
 * Time:2022/1/27 14:05
 * Author:
 * Description:
 */
class VideoContentAdapter(val context: Context) :
    CommonAdapter<VideoItem>(context, R.layout.adapter_item_video_content) {
    override fun convert(viewHolder: ViewHolder, item: VideoItem, position: Int) {
        viewHolder.setText(R.id.tvTitle, item.name)
            .setText(R.id.tvSubTitle, Formatter.formatFileSize(context, item.size))
        val path = Utils.getSaveBitmapPath(item.filePath)
        if (File(path).exists()) {
            x.image().bind(viewHolder.getView(R.id.ivThumbnail), path)
        } else {
            val mmr = MediaMetadataRetriever()
            val filePath = item.filePath
            Log.i(Utils.TAG, "filePath:$filePath")
            mmr.setDataSource(filePath)
            Utils.saveBitmap(filePath, mmr.frameAtTime)
        }
    }
}