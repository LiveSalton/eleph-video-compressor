package com.salton123.eleph.video.compressor.adapter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.format.Formatter
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.salton123.eleph.R
import com.salton123.eleph.video.compressor.adapter.holder.ContentStubViewHolder
import com.salton123.eleph.video.compressor.model.SqueezeProp
import com.salton123.eleph.video.compressor.model.VideoItem
import com.salton123.eleph.video.compressor.persistence.VideoDao
import com.salton123.eleph.video.compressor.task.FFmpegCompressor
import com.salton123.eleph.video.compressor.ui.SqueezeOptionPopupComp
import com.salton123.eleph.video.compressor.ui.VideoMenuPopupComp
import com.salton123.eleph.video.compressor.ui.VideoPlayActivity
import kt.runOnUi
import java.io.File
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.properties.Delegates

/**
 * Time:2022/1/30 11:53 下午
 * Author:
 * Description:
 */
class RecyclerContentAdapter(val recyclerView: RecyclerView) : RecyclerView.Adapter<ContentStubViewHolder>(), IAdapterDiffer {
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
        if (item.squeezeState != 2) {
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
        } else {
            holder.tvProgress.text = "success"
            holder.tvSubTitle.text = "${Formatter.formatFileSize(context, item.size)} -> ${Formatter.formatFileSize(context, File(item.squeezeSavePath).length())}"
        }


        Glide.with(context).load(item.filePath)
            .thumbnail(0.3f)
            .placeholder(R.drawable.ic_placeholder)
            .into(holder.ivThumbnail)
        holder.tvMore.setOnClickListener {
            VideoMenuPopupComp().apply {
                arguments = Bundle().apply {
                    putString("filePath", item.filePath)
                    putSerializable("videoItem", item)
                }
                show((context as Activity).fragmentManager, "VideoMenuPopupComp")
                attachAdapter(this@RecyclerContentAdapter)
            }
        }
        holder.itemView.setOnClickListener {
            if (item.isSqueezeSuccess() && File(item.squeezeSavePath).exists()) {
                context.startActivity(Intent(context, VideoPlayActivity::class.java).apply {
                    putExtra("videoItem", item)
                    putExtra("isPlaySqueeze", true)
                })
            } else {
                SqueezeOptionPopupComp().apply {
                    arguments = Bundle().apply {
                        putSerializable("videoItem", item)
                        putInt("position", position)
                    }
                    show((context as Activity).fragmentManager, "SqueezeOptionPopupComp")
                    attachAdapter(this@RecyclerContentAdapter)
                }
            }
        }
    }

    fun startSqueeze(item: VideoItem, position: Int, prop: SqueezeProp) {
        item.squeezeState = 1
        FFmpegCompressor.squeeze(prop) {
            runOnUi {
                item.squeezeProgress = it
                if (it == 100) {
                    item.squeezeState = 2
                    item.squeezeSavePath = prop.getSavePath()
                    VideoDao.updateVideo(item)
                } else if (it < 0) {
                    item.squeezeState = 3
                }
//                recyclerView.adapter?.notifyDataSetChanged()
                recyclerView.adapter?.notifyItemChanged(position)
//                notifyItemChanged(position)
            }
        }
    }

    fun notifyItemChange(videoItem: VideoItem) {
        val index = dataList.indexOf(videoItem)
        notifyItemChanged(index)
    }

    fun notifyItemDelete(videoItem: VideoItem) {
        val index = dataList.indexOf(videoItem)
        notifyItemRemoved(index)
    }

    override fun getItemCount(): Int = dataList.size

    fun setData(list: CopyOnWriteArrayList<VideoItem>) {
        dataList = list
    }
}