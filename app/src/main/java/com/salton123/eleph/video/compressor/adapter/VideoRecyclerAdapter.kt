package com.salton123.eleph.video.compressor.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.salton123.eleph.R
import com.salton123.eleph.video.compressor.adapter.holder.VideoRecyclerViewHolder
import com.salton123.eleph.video.compressor.task.MediaFileScanTask
import com.salton123.eleph.video.compressor.utils.Utils
import com.salton123.eleph.video.compressor.widget.GridSpacingItemDecoration
import com.salton123.util.ScreenUtils

/**
 * Time:2022/1/29 11:42 上午
 * Author:
 * Description:
 */
class VideoRecyclerAdapter : RecyclerView.Adapter<VideoRecyclerViewHolder>(), IAdapterDiffer {

    private var dataList: MutableList<Long> = mutableListOf()
//    private var dataList: MutableList<Long> by Delegates.observable(mutableListOf()) { _, old, new ->
//        onDiff(old, new,
//            { o, n -> o == n },
//            { o, n -> o == n })
//    }

//    fun add(item: Long) {
//        if (!dataList.contains(item)) {
//            dataList.add(item)
//            dataList.sortByDescending { it }
//        }
//    }


    fun add(item: Long) {
        if (!dataList.contains(item)) {
            dataList.add(item)
            dataList.sortByDescending { it }
        }
        notifyDataSetChanged()
    }

    fun setData(dataList: MutableList<Long>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoRecyclerViewHolder {
        return VideoRecyclerViewHolder(View.inflate(parent.context, R.layout.view_stub_video_recycler, null))
    }

    override fun onBindViewHolder(holder: VideoRecyclerViewHolder, position: Int) {
        val item = dataList[position]
        val context = holder.itemView.context
        val recyclerView = holder.recyclerView
        val mAdapter = RecyclerContentAdapter()
        holder.tvTitle.text = Utils.getDateTitle(item)
//        mAdapter.setHasStableIds(true)
        recyclerView.adapter = mAdapter
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        var mSplitItemDecoration: GridSpacingItemDecoration? = null
        if (recyclerView.itemDecorationCount == 0) {
            mSplitItemDecoration = GridSpacingItemDecoration(2, ScreenUtils.dp2px(8f), true)
            recyclerView.addItemDecoration(mSplitItemDecoration)
        } else {
            if (mSplitItemDecoration != null) {
                recyclerView.removeItemDecoration(mSplitItemDecoration)
                mSplitItemDecoration = GridSpacingItemDecoration(2, ScreenUtils.dp2px(8f), false)
                recyclerView.addItemDecoration(mSplitItemDecoration)
            }
        }

        recyclerView.layoutManager = GridLayoutManager(context, 2)
        MediaFileScanTask.videoMap[item]?.let {
            mAdapter.setData(it)
        }
//        MediaFileScanTask.onDataSetChange = { type, data ->
//            if (type == item) {
//                data?.let { mAdapter.setData(it) }
//            }
//        }
    }

    override fun getItemCount(): Int = dataList.size
}