package com.salton123.eleph.video.compressor.adapter

import android.app.Activity
import android.os.Bundle
import android.text.format.Formatter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.salton123.eleph.R
import com.salton123.eleph.video.compressor.adapter.holder.ContentStubViewHolder
import com.salton123.eleph.video.compressor.adapter.holder.NullStubViewHolder
import com.salton123.eleph.video.compressor.adapter.holder.TitleViewHolder
import com.salton123.eleph.video.compressor.model.*
import com.salton123.eleph.video.compressor.ui.SqueezeOptionPopupComp
import com.salton123.eleph.video.compressor.ui.VideoMenuPopupComp
import java.io.File

/**
 * Time:2022/2/22 11:10 上午
 * Author:
 * Description:
 */
class VideoRecyclerTypeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var dataList: MutableList<IMultiType> = mutableListOf()

    override fun getItemViewType(position: Int): Int {
        return dataList[position].type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(viewType, null, false)
        return when (viewType) {
            TYPE_TITLE -> {
                TitleViewHolder(itemView)
            }
            TYPE_CONTENT_STUB -> {
                ContentStubViewHolder(itemView)
            }
            else -> {
                NullStubViewHolder(itemView)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val dataItem = dataList[position]
        when (holder) {
            is TitleViewHolder -> {
                if (dataItem is TitleType) {
                    holder.tvTitle.text = dataItem.title
                }
            }
            is ContentStubViewHolder -> {
                if (dataItem is ContentStubType) {
                    var item = dataItem.data
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
//                            attachAdapter(this@RecyclerContentAdapter)
                        }
                    }
                    holder.itemView.setOnClickListener {
                        SqueezeOptionPopupComp().apply {
                            arguments = Bundle().apply {
                                putSerializable("videoItem", item)
                                putInt("position", position)
                            }
                            show((context as Activity).fragmentManager, "SqueezeOptionPopupComp")
//                            attachAdapter(this@RecyclerContentAdapter)
                        }
                    }
                }

            }
            else -> {

            }
        }
    }

    override fun getItemCount(): Int = dataList.size

    fun setData(list: MutableList<IMultiType>) {
        this.dataList = list
        notifyDataSetChanged()
    }
}