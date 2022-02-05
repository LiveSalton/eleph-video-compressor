package com.salton123.eleph.video.compressor.adapter.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.salton123.eleph.R

/**
 * Time:2022/1/30 11:43 下午
 * Author:
 * Description:
 */
class ContentStubViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
    var tvProgress: TextView = itemView.findViewById(R.id.tvProgress)
    var tvSubTitle: TextView = itemView.findViewById(R.id.tvSubTitle)
    var tvMore: TextView = itemView.findViewById(R.id.tvMore)
    var ivThumbnail: ImageView = itemView.findViewById(R.id.ivThumbnail)
}