package com.salton123.eleph.video.compressor.adapter

import android.text.format.Formatter
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.salton123.eleph.R
import com.salton123.eleph.video.compressor.adapter.holder.ContentStubViewHolder
import com.salton123.eleph.video.compressor.model.SqueezeProp
import com.salton123.eleph.video.compressor.model.VideoItem
import com.salton123.eleph.video.compressor.task.FFmpegCompressor
import com.salton123.eleph.video.kt.runOnUi
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.properties.Delegates

/**
 * Time:2022/1/30 11:53 下午
 * Author:
 * Description:
 */
class RecyclerContentAdapter : RecyclerView.Adapter<ContentStubViewHolder>(), IAdapterDiffer {
    private var dataList: MutableList<VideoItem> by Delegates.observable(mutableListOf()) { _, old, new ->
        onDiff(old, new,
            { o, n -> o.filePath == n.filePath },
            { o, n ->
                o.filePath == n.filePath
            })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentStubViewHolder {
        return ContentStubViewHolder(View.inflate(parent.context, R.layout.adapter_item_video_content, null))
    }

    override fun onBindViewHolder(holder: ContentStubViewHolder, position: Int) {
        val item = dataList[position]
        val context = holder.itemView.context
        holder.tvTitle.text = item.name
        var squeezeTip = if (item.squeezeProgress == -1) {
            "failed"
        } else if (item.squeezeProgress > 100) {
            "processing"
        } else if (item.squeezeProgress == 100) {
            "success"
        } else {
            "${item.squeezeProgress}%"
        }
        holder.tvProgress.text = squeezeTip
        holder.tvSubTitle.text = Formatter.formatFileSize(context, item.size)
        Glide.with(context).load(item.filePath)
            .thumbnail(0.3f)
            .placeholder(R.drawable.ic_placeholder)
            .into(holder.ivThumbnail)
        holder.itemView.setOnClickListener {
//            VideoInfoPopupComp().apply {
//                arguments = Bundle().apply {
//                    putString("filePath", item.filePath)
//                    putSerializable("videoItem", item)
//                }
//                show((context as Activity).fragmentManager, "VideoInfoPopupComp")
//            }
            item.squeezeState = 1
            var height = (item.height / 2) - (item.height / 2) % 2
            var width = (item.width / 2) - (item.width / 2) % 2
            FFmpegCompressor.squeeze(SqueezeProp(item.filePath, scale = "$width:$height")) {
                runOnUi {
                    item.squeezeProgress = it
                    if (it == 100) {
                        item.squeezeState = 2
                    } else if (it < 0) {
                        item.squeezeState = 0
                    }
                    notifyItemChanged(position)
                }
            }
//            FFmpegCompressor.compress(item.filePath) {
//                runOnUi {
//                    item.compressProgress = it
//                    notifyItemChanged(position)
//                }
//            }
        }
    }

    override fun getItemCount(): Int = dataList.size

    fun setData(list: CopyOnWriteArrayList<VideoItem>) {
        dataList = list
    }
}