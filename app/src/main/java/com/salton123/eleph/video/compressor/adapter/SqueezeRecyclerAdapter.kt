package com.salton123.eleph.video.compressor.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.salton123.eleph.R
import com.salton123.eleph.video.compressor.adapter.holder.SqueezeRecyclerHolder
import com.salton123.eleph.video.compressor.model.VideoItem

/**
 * Time:2022/2/2 8:46 下午
 * Author:
 * Description:
 */
class SqueezeRecyclerAdapter : RecyclerView.Adapter<SqueezeRecyclerHolder>() {
    private val dataList: MutableList<VideoItem> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SqueezeRecyclerHolder {
        return SqueezeRecyclerHolder(View.inflate(parent.context, R.layout.adapter_item_squeeze))
    }

    override fun onBindViewHolder(holder: SqueezeRecyclerHolder, position: Int) {
    }

    override fun getItemCount(): Int = dataList.size
}