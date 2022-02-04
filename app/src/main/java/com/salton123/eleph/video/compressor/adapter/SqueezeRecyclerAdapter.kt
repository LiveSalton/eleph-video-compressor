package com.salton123.eleph.video.compressor.adapter

import android.app.Activity
import android.os.Bundle
import android.text.format.Formatter
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.salton123.eleph.R
import com.salton123.eleph.video.compressor.adapter.holder.SqueezeRecyclerHolder
import com.salton123.eleph.video.compressor.model.VideoItem
import com.salton123.eleph.video.compressor.ui.VideoInfoPopupComp
import kotlin.properties.Delegates

/**
 * Time:2022/2/2 8:46 下午
 * Author:
 * Description:
 */
class SqueezeRecyclerAdapter : RecyclerView.Adapter<SqueezeRecyclerHolder>(), IAdapterDiffer {
    private var dataList: MutableList<VideoItem> by Delegates.observable(mutableListOf()) { _, old, new ->
        onDiff(old, new,
            { o, n -> o.filePath == n.filePath },
            { o, n ->
                o.filePath == n.filePath
            })
    }

    private fun setData(data: MutableList<VideoItem>) {
        this.dataList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SqueezeRecyclerHolder {
        return SqueezeRecyclerHolder(View.inflate(parent.context, R.layout.adapter_item_squeeze, null))
    }

    override fun onBindViewHolder(holder: SqueezeRecyclerHolder, position: Int) {
        val item = dataList[position]
        val context = holder.itemView.context
        holder.tvTitle.text = item.name
        holder.tvProgress.text = "${item.squeezeProgress}%"
        holder.tvSubTitle.text = Formatter.formatFileSize(context, item.size)
        Glide.with(context).load(item.filePath)
            .thumbnail(0.3f)
            .placeholder(R.drawable.ic_placeholder)
            .into(holder.ivThumbnail)
        holder.itemView.setOnClickListener {
            VideoInfoPopupComp().apply {
                arguments = Bundle().apply {
                    putString("filePath", item.filePath)
                    putSerializable("videoItem", item)
                }
                show((context as Activity).fragmentManager, "VideoInfoPopupComp")
            }
        }
    }

    override fun getItemCount(): Int = dataList.size
}