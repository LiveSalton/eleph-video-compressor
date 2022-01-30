package com.salton123.eleph.video.compressor.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.salton123.eleph.R
import com.salton123.eleph.video.compressor.adapter.holder.VideoRecyclerViewHolder
import com.salton123.eleph.video.compressor.model.TYPE_TITLE
import com.salton123.eleph.video.compressor.task.MediaFileScanTask
import com.salton123.eleph.video.compressor.utils.Utils

/**
 * Time:2022/1/29 11:42 上午
 * Author:
 * Description:
 */
class VideoRecyclerAdapter : RecyclerView.Adapter<VideoRecyclerViewHolder>() {
    private var dataList: MutableList<Long> = mutableListOf()

    fun add(item: Long) {
        if (!dataList.contains(item)) {
            dataList.add(item)
            dataList.sortByDescending { it }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoRecyclerViewHolder {
        return VideoRecyclerViewHolder(View.inflate(parent.context, R.layout.view_stub_video_recycler, null))
    }

    override fun onBindViewHolder(holder: VideoRecyclerViewHolder, position: Int) {
        val item = dataList[position]
        val context = holder.itemView.context
        val mAdapter = RecyclerContentAdapter()
        holder.recyclerView.adapter = mAdapter
        holder.tvTitle.text = Utils.getDateTitle(item)
        GridLayoutManager(context, 4).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (mAdapter.getItemViewType(position) == TYPE_TITLE) {
                        4
                    } else {
                        2
                    }
                }
            }
            holder.recyclerView.layoutManager = this
        }
        MediaFileScanTask.videoMap[item]?.let { mAdapter.setData(it) }
    }

    override fun getItemCount(): Int = dataList.size
}