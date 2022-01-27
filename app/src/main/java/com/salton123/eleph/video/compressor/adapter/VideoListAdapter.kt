package com.salton123.eleph.video.compressor.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.TextView
import com.salton123.bookmarksbrowser.R
import com.salton123.eleph.video.compressor.model.ContentType
import com.salton123.eleph.video.compressor.model.IMultiType
import com.salton123.eleph.video.compressor.model.TYPE_CONTENT
import com.salton123.eleph.video.compressor.model.TitleType

/**
 * Time:2022/1/27 11:18
 * Author:
 * Description:
 */
class VideoListAdapter : BaseAdapter() {
    private val dataList: MutableList<IMultiType> = mutableListOf()

    fun add(item: IMultiType) {
        dataList.add(item)
    }

    fun addAll(datas: MutableList<IMultiType>) {
        dataList.addAll(datas)
    }

    override fun getCount(): Int = dataList.size

    override fun getItem(position: Int): Any = dataList[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int {
        return dataList[position].type
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View
        var titleViewHolder: TitleViewHolder? = null
        var contentViewHolder: ContentViewHolder? = null
        when (getItemViewType(position)) {
            TYPE_CONTENT -> {
                if (convertView == null) {
                    view = View.inflate(parent!!.context, R.layout.view_stub_content, null)
                    contentViewHolder = ContentViewHolder(view)
                    view.tag = contentViewHolder
                } else {
                    view = convertView
                    if (view.tag is ContentViewHolder) {
                        contentViewHolder = view.tag as ContentViewHolder
                    }
                }
                val item = getItem(position)
                if (item is ContentType) {
                    val adapter = VideoContentAdapter(parent!!.context)
                    contentViewHolder?.gvVideos?.adapter = adapter
                    adapter.addAll(item.data)
                    adapter.notifyDataSetChanged()
                }
            }
            else -> {
                if (convertView == null) {
                    view = View.inflate(parent!!.context, R.layout.view_stub_title, null)
                    titleViewHolder = TitleViewHolder(view)
                    view.tag = titleViewHolder
                } else {
                    view = convertView
                    if (view.tag is TitleViewHolder) {
                        titleViewHolder = view.tag as TitleViewHolder
                    }
                }
                val item = getItem(position)
                if (item is TitleType) {
                    titleViewHolder?.tvTitle?.text = item.title
                }
            }
        }
        return view
    }

    inner class TitleViewHolder(itemView: View) {
        var tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
    }

    inner class ContentViewHolder(itemView: View) {
        var gvVideos: GridView = itemView.findViewById(R.id.gridView)
    }
}