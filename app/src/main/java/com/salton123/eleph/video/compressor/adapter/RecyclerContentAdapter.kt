package com.salton123.eleph.video.compressor.adapter

import android.app.Activity
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
import com.salton123.eleph.video.compressor.observe.Observable
import com.salton123.eleph.video.compressor.persistence.VideoDao
import com.salton123.eleph.video.compressor.task.FFmpegCompressor
import com.salton123.eleph.video.compressor.ui.HomeActivity
import com.salton123.eleph.video.compressor.ui.SqueezeOptionPopupComp
import com.salton123.eleph.video.compressor.ui.VideoMenuPopupComp
import kt.runOnUi
import java.io.File
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.properties.Delegates

/**
 * Time:2022/1/30 11:53 下午
 * Author:
 * Description:
 */
class RecyclerContentAdapter(val recyclerView: RecyclerView) : RecyclerView.Adapter<ContentStubViewHolder>(), IAdapterDiffer, Observable<VideoItem> {
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
        if (!item.isSqueezeSuccess()) {
            when {
                item.squeezeProgress in 1..99 -> {
                    holder.tvProgress.text = "${item.squeezeProgress}%"
                    holder.tvProgress.visibility = View.VISIBLE
                }
                item.squeezeProgress < 0 -> {
                    holder.tvProgress.text = "failed"
                    holder.tvProgress.visibility = View.VISIBLE
                }
                item.squeezeProgress > 100 -> {
                    holder.tvProgress.text = "processing"
                    holder.tvProgress.visibility = View.VISIBLE
                }
                else -> {
                    holder.tvProgress.visibility = View.GONE
                }
            }
            holder.tvSubTitle.text = Formatter.formatFileSize(context, item.size)
        } else {
            holder.tvProgress.visibility = View.GONE
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
            SqueezeOptionPopupComp().apply {
                arguments = Bundle().apply {
                    putSerializable("videoItem", item)
                    putInt("position", position)
                }
                show((context as Activity).fragmentManager, "SqueezeOptionPopupComp")
                attachAdapter(this@RecyclerContentAdapter)
            }
//            if (item.isSqueezeSuccess() && File(item.squeezeSavePath).exists()) {
//                context.startActivity(Intent(context, VideoPlayActivity::class.java).apply {
//                    putExtra("videoItem", item)
//                    putExtra("isPlaySqueeze", true)
//                })
//            } else {
//                SqueezeOptionPopupComp().apply {
//                    arguments = Bundle().apply {
//                        putSerializable("videoItem", item)
//                        putInt("position", position)
//                    }
//                    show((context as Activity).fragmentManager, "SqueezeOptionPopupComp")
//                    attachAdapter(this@RecyclerContentAdapter)
//                }
//            }
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
                    item.squeezeSize = File(prop.getSavePath()).length()
                    item.slimSize = item.size - item.squeezeSize
                    VideoDao.updateVideo(item)
                    (recyclerView.context as HomeActivity?)?.updateClearInfo()
                } else if (it < 0) {
                    item.squeezeState = 3
                }
                recyclerView.adapter?.notifyItemChanged(position)
            }
        }
    }

    override fun observe(data: VideoItem) {
        val index = dataList.indexOf(data)
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