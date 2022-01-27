package com.salton123.eleph.video.compressor.adapter

import android.content.Context
import android.text.format.Formatter
import com.salton123.adapter.abslistview.CommonAdapter
import com.salton123.adapter.abslistview.ViewHolder
import com.salton123.bookmarksbrowser.R
import com.salton123.eleph.video.compressor.model.VideoItem
import org.xutils.x

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
//        x.image().bind(viewHolder.getView(R.id.ivThumbnail), item.filePath)
    }
}