package com.salton123.eleph.video.compressor.ui

import android.os.Bundle
import android.widget.ListView
import com.salton123.base.BaseActivity
import com.salton123.base.feature.ImmersionFeature
import com.salton123.base.feature.PermissionFeature
import com.salton123.bookmarksbrowser.R
import com.salton123.eleph.video.compressor.adapter.VideoListAdapter
import com.salton123.eleph.video.compressor.model.ContentType
import com.salton123.eleph.video.compressor.model.TitleType
import com.salton123.eleph.video.compressor.model.VideoItem
import com.salton123.eleph.video.compressor.persistence.VideoDao
import com.salton123.eleph.video.compressor.task.MediaFileScanTask
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Time:2022/1/27 11:04
 * Author:
 * Description:
 */
class MainActivity : BaseActivity() {
    override fun getLayout(): Int = R.layout.activity_main
    private lateinit var mImmersionFeature: ImmersionFeature
    private lateinit var listView: ListView
    private lateinit var mAdapter: VideoListAdapter
    private var dataMap: HashMap<String, MutableList<VideoItem>> = hashMapOf()
    private val simpleDateFormat = SimpleDateFormat("yyyy_MM_dd", Locale.getDefault())
    override fun initVariable(savedInstanceState: Bundle?) {
        mImmersionFeature = ImmersionFeature(this)
        addFeature(mImmersionFeature)
        addFeature(PermissionFeature(this))
        MediaFileScanTask.launch()
    }

    override fun initViewAndData() {
        listView = findViewById(R.id.listView)
        mAdapter = VideoListAdapter()
        listView.adapter = mAdapter
        VideoDao.findAll()?.forEach {
            dataMap[simpleDateFormat.format(it.createdAt)]?.apply {
                add(it)
            } ?: kotlin.run {
                dataMap[simpleDateFormat.format(it.createdAt)] = mutableListOf(it)
            }
        }
        dataMap.forEach {
            mAdapter.add(TitleType(it.key))
            mAdapter.add(ContentType(it.value))
        }
        mAdapter.notifyDataSetChanged()
    }
}