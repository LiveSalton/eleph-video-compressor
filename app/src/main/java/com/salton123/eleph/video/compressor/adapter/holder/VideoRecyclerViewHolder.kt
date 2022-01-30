package com.salton123.eleph.video.compressor.adapter.holder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.salton123.eleph.R

/**
 * Time:2022/1/30 11:43 下午
 * Author:
 * Description:
 */
class VideoRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
    var recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
}