package com.salton123.eleph.video.compressor.ui

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.salton123.base.BaseActivity
import com.salton123.eleph.R
import com.salton123.eleph.video.compressor.adapter.SqueezeRecyclerAdapter

/**
 * Time:2022/2/2 5:01 下午
 * Author:
 * Description:
 */
class SqueezeRecyclerActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_video_compress
    private lateinit var recyclerView: RecyclerView
    private lateinit var mAdapter: SqueezeRecyclerAdapter
    override fun initViewAndData() {
        super.initViewAndData()
        recyclerView = findViewById(R.id.recyclerView)
        mAdapter = SqueezeRecyclerAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}