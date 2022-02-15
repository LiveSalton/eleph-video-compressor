package com.salton123.eleph.video.compressor.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Process
import android.text.format.Formatter
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.salton123.base.DelegateActivity
import com.salton123.base.feature.ImmersionFeature
import com.salton123.eleph.BuildConfig
import com.salton123.eleph.R
import com.salton123.eleph.video.compressor.adapter.VideoRecyclerAdapter
import com.salton123.eleph.video.compressor.persistence.VideoDao
import com.salton123.eleph.video.compressor.task.FFmpegCompressor
import com.salton123.eleph.video.compressor.task.MediaFileScanTask
import com.salton123.service.SqueezeService
import kt.runOnUi

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
    private lateinit var tvMore: TextView
    private lateinit var tvClearInfo: TextView
    private lateinit var mAdapter: VideoRecyclerAdapter
    override fun initVariable(savedInstanceState: Bundle?) {
        mImmersionFeature = ImmersionFeature(this)
        addFeature(mImmersionFeature)
        if (isPermissionGrant()) {
            MediaFileScanTask.launch()
        } else {
            requestPermissions(permissions, REQUEST_CODE)
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

    private fun initListView() {
        recyclerView = findViewById(R.id.recyclerView)
        llEmptyView = findViewById(R.id.llEmptyView)
        tvMore = findViewById(R.id.tvMore)
        tvClearInfo = findViewById(R.id.tvClearInfo)
        mAdapter = VideoRecyclerAdapter()
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        MediaFileScanTask.onTypeListChange = { list ->
            runOnUi {
                if (list.isEmpty()) {
                    llEmptyView.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                } else {
                    mAdapter.setData(list)
                    recyclerView.scrollToPosition(0)
                    llEmptyView.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                }
            }
        }
        tvMore.setOnClickListener {
            openActivity(SqueezeRecyclerActivity::class.java, Bundle())
        }
        updateClearInfo()
    }

    private fun updateClearInfo() {
        val clearInfo = VideoDao.getClearInfo()
        val clearText = String.format("累计压缩%d个视频，节省%s内存",
            clearInfo.squeezeCount,
            Formatter.formatFileSize(activity(), clearInfo.squeezeTotal))
        tvClearInfo.text = clearText
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