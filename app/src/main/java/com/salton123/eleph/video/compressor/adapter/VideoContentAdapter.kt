package com.salton123.eleph.video.compressor.adapter

import android.content.Context
import android.text.format.Formatter
import com.bumptech.glide.Glide
import com.salton123.adapter.abslistview.CommonAdapter
import com.salton123.adapter.abslistview.ViewHolder
import com.salton123.eleph.R
import com.salton123.eleph.video.compressor.model.VideoItem

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
        Glide.with(context).load(item.filePath)
            .centerCrop()
            .thumbnail(0.3f)
            .placeholder(R.drawable.ic_placeholder)
            .into(viewHolder.getView(R.id.ivThumbnail))
    }
}