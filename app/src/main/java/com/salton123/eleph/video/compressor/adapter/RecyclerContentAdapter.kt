package com.salton123.eleph.video.compressor.adapter

import android.text.format.Formatter
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.salton123.eleph.R
import com.salton123.eleph.video.compressor.adapter.holder.ContentStubViewHolder
import com.salton123.eleph.video.compressor.model.VideoItem
import com.salton123.eleph.video.compressor.utils.Utils
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Time:2022/1/30 11:53 下午
 * Author:
 * Description:
 */
class RecyclerContentAdapter : RecyclerView.Adapter<ContentStubViewHolder>() {
    private var dataList: MutableList<VideoItem> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentStubViewHolder {
        return ContentStubViewHolder(View.inflate(parent.context, R.layout.adapter_item_video_content, null))
    }

    override fun onBindViewHolder(holder: ContentStubViewHolder, position: Int) {
        val item = dataList[position]
        val context = holder.itemView.context
        holder.tvTitle.text = item.name
        holder.tvSubTitle.text = Formatter.formatFileSize(context, item.size) + "," + Utils.getDateTitle(item.dateTime)
        Glide.with(context).load(item.filePath)
            .centerCrop()
            .thumbnail(0.3f)
            .placeholder(R.drawable.ic_placeholder)
            .into(holder.ivThumbnail)
    }

    override fun getItemCount(): Int = dataList.size

    fun setData(list: CopyOnWriteArrayList<VideoItem>) {
        dataList.addAll(list)
        notifyDataSetChanged()
    }
}