package com.salton123.eleph.video.compressor.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Process
import android.text.format.Formatter
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.salton123.base.DelegateActivity
import com.salton123.base.feature.ImmersionFeature
import com.salton123.eleph.BuildConfig
import com.salton123.eleph.R
import com.salton123.eleph.video.compressor.adapter.VideoRecyclerTypeAdapter
import com.salton123.eleph.video.compressor.model.*
import com.salton123.eleph.video.compressor.persistence.VideoDao
import com.salton123.eleph.video.compressor.task.FFmpegCompressor
import com.salton123.eleph.video.compressor.task.MediaFileScanTask
import com.salton123.service.SqueezeService
import com.salton123.utils.DateUtils
import kt.runOnUi
import java.util.*

/**
 * Time:2022/1/29 11:40 上午
 * Author:
 * Description:
 */

class HomeActivity : DelegateActivity() {
    private val TAG = "HomeActivity"
    private val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private val REQUEST_CODE = 101
    override fun getLayout(): Int = R.layout.activity_home
    private lateinit var mImmersionFeature: ImmersionFeature
    private lateinit var recyclerView: RecyclerView
    private lateinit var llEmptyView: LinearLayout
    private lateinit var flAdContainer: FrameLayout
    private lateinit var tvMore: TextView
    private lateinit var tvClearInfo: TextView
    private lateinit var mAdapter: VideoRecyclerTypeAdapter
    override fun initVariable(savedInstanceState: Bundle?) {
        mImmersionFeature = ImmersionFeature(this)
        addFeature(mImmersionFeature)
        if (isPermissionGrant()) {
            MediaFileScanTask.launch()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissions, REQUEST_CODE)
            }
        }
    }

    private fun isPermissionGrant(): Boolean {
        var result = false
        for (item in permissions) {
            result = result and (checkPermission(item, Process.myPid(), Process.myUid()) == PackageManager.PERMISSION_GRANTED)
        }
        return result
    }

    override fun initViewAndData() {
        initListView()
        if (BuildConfig.DEBUG) {
            FFmpegCompressor.help()
        }
        ContextCompat.startForegroundService(this, Intent(this, SqueezeService::class.java))
    }

    private val dataList: MutableList<IMultiType> = mutableListOf()

    private fun initListView() {
        recyclerView = findViewById(R.id.recyclerView)
        llEmptyView = findViewById(R.id.llEmptyView)
        tvMore = findViewById(R.id.tvMore)
        tvClearInfo = findViewById(R.id.tvClearInfo)
        flAdContainer = findViewById(R.id.flAdContainer)
        mAdapter = VideoRecyclerTypeAdapter()
        val gridLayoutManager = GridLayoutManager(this, 4)
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = gridLayoutManager
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (dataList[position].type) {
                    TYPE_TITLE -> {
                        4
                    }
                    TYPE_CONTENT_STUB -> {
                        2
                    }
                    else -> {
                        4
                    }
                }
            }
        }
        MediaFileScanTask.onTypeListChange = { list ->
            runOnUi {
                if (list.isEmpty()) {
                    llEmptyView.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                } else {
                    dataList.clear()
                    list.forEach { timestamp ->
                        val title = DateUtils.timeFormatNearby(Date(timestamp))
                        dataList.add(TitleType(title))
                        MediaFileScanTask.videoMap[timestamp]?.let { videoList ->
                            videoList.forEach {
                                dataList.add(ContentStubType(it))
                            }
                        }
                    }
                    mAdapter.setData(dataList)
                    recyclerView.scrollToPosition(0)
                    llEmptyView.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                }
            }
        }
        tvMore.setOnClickListener {
//            AdMobManager.loadAd(this)
//            AdMobManager.loadBannerAd(this, flAdContainer)
            openActivity(SettingActivity::class.java, Bundle())
        }
        updateClearInfo()
    }

    fun updateClearInfo() {
        VideoDao.getClearInfo { clearInfo ->
            val clearText = String.format("累计压缩%d个视频，节省%s内存",
                clearInfo?.squeezeCount,
                Formatter.formatFileSize(activity(), clearInfo?.squeezeTotal ?: 0))
            runOnUi {
                tvClearInfo.text = clearText
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "请授予全部权限", Toast.LENGTH_LONG).show()
                finish()
            } else {
                MediaFileScanTask.launch()
            }
        }
    }
}