package com.salton123.eleph.video.compressor.adapter

import android.text.format.Formatter
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.salton123.eleph.R
import com.salton123.eleph.video.compressor.model.ContentStubType
import com.salton123.eleph.video.compressor.model.IMultiType
import com.salton123.eleph.video.compressor.model.TYPE_CONTENT_STUB
import com.salton123.eleph.video.compressor.model.TitleType
import com.salton123.eleph.video.compressor.utils.Utils
import com.salton123.eleph.video.kt.log
import kotlin.properties.Delegates

/**
 * Time:2022/1/29 11:42 上午
 * Author:
 * Description:
 */
class VideoRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(), IAdapterDiffer {
    private var dataList: MutableList<IMultiType> by Delegates.observable(mutableListOf()) { _, old, new ->
        onDiff(old, new,
            { o, n -> o.type == n.type },
            { o, n ->
                if (o is TitleType && n is TitleType) {
                    o.title == n.title
                } else if (o is ContentStubType && n is ContentStubType) {
                    o.data.filePath == n.data.filePath
                } else {
                    false
                }
            })
    }
//
//    private var dataList: MutableList<IMultiType> by Delegates.observable(mutableListOf()) { _, oldList, newList ->
//        val differ = object : DiffUtil.Callback() {
//            override fun getOldListSize(): Int = oldList.size
//
//            override fun getNewListSize(): Int = newList.size
//
//            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//                return try {
//                    oldList[oldItemPosition]?.type ?: 0 == newList[newItemPosition]?.type ?: -1
//                } catch (ex: Exception) {
//                    false
//                }
//            }
//
//            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//                val o = oldList[oldItemPosition]
//                val n = newList[newItemPosition]
//                if (o is TitleType && n is TitleType) {
//                    return o.title == n.title
//                } else if (o is ContentStubType && n is ContentStubType) {
//                    return o.data.filePath == n.data.filePath
//                } else {
//                    return false
//                }
//            }
//        }
//        DiffUtil.calculateDiff(differ).dispatchUpdatesTo(this)
//    }

    fun add(item: IMultiType) {
        dataList.add(item)
    }

    override fun getItemViewType(position: Int): Int {
        return dataList[position].type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_CONTENT_STUB -> {
                return ContentStubViewHolder(View.inflate(parent.context, R.layout.adapter_item_video_content, null))
            }
            else -> {
                return TitleViewHolder(View.inflate(parent.context, R.layout.view_stub_title, null))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = dataList[position]
        val context = holder.itemView.context
        when (holder) {
            is ContentStubViewHolder -> {
                if (item is ContentStubType) {
                    val title = Utils.getDateTitle(item.data.createdAt)
                    holder.tvTitle.text = item.data.name
//                    holder.tvTitle.text = title
//                    holder.tvSubTitle.text = Formatter.formatFileSize(context, item.data.size)
                    holder.tvSubTitle.text = Formatter.formatFileSize(context, item.data.size) + "," + title
                    Glide.with(context).load(item.data.filePath)
                        .centerCrop()
                        .thumbnail(0.3f)
                        .placeholder(R.drawable.ic_placeholder)
                        .into(holder.ivThumbnail)
                }
            }
            is TitleViewHolder -> {
                if (item is TitleType) {
                    holder.tvTitle.text = item.title
                }
            }
            else -> {
                log("can not parse holder:${holder}")
            }
        }
    }

    override fun getItemCount(): Int = dataList.size
    fun setData(dataList: MutableList<IMultiType>) {
        this.dataList = dataList
    }

    inner class TitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
    }

    inner class ContentStubViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        var tvSubTitle: TextView = itemView.findViewById(R.id.tvSubTitle)
        var ivThumbnail: ImageView = itemView.findViewById(R.id.ivThumbnail)
    }
}