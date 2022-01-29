package com.salton123.eleph.video.compressor.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Process
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import com.salton123.base.BaseActivity
import com.salton123.base.feature.ImmersionFeature
import com.salton123.eleph.R
import com.salton123.eleph.video.compressor.adapter.VideoListAdapter
import com.salton123.eleph.video.compressor.model.ContentType
import com.salton123.eleph.video.compressor.model.TitleType
import com.salton123.eleph.video.compressor.model.VideoItem
import com.salton123.eleph.video.compressor.task.MediaFileScanTask
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Time:2022/1/27 11:04
 * Author:
 * Description:
 */
class MainActivity : BaseActivity() {
    private val TAG = "MainActivity"
    private val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private val REQUEST_CODE = 101
    override fun getLayout(): Int = R.layout.activity_main
    private lateinit var mImmersionFeature: ImmersionFeature
    private lateinit var listView: ListView
    private lateinit var mAdapter: VideoListAdapter
    private var dataMap: HashMap<String, CopyOnWriteArrayList<VideoItem>> = hashMapOf()
    private val simpleDateFormat = SimpleDateFormat("yyyy_MM_dd", Locale.getDefault())
    override fun initVariable(savedInstanceState: Bundle?) {
        mImmersionFeature = ImmersionFeature(this)
        addFeature(mImmersionFeature)
        if (isPermissionGrant()) {
            MediaFileScanTask.launch()
        } else {
            requestPermissions(permissions, REQUEST_CODE)
        }
    }

    fun isPermissionGrant(): Boolean {
        var result = false
        for (item in permissions) {
            result = result and (checkPermission(item, Process.myPid(), Process.myUid()) == PackageManager.PERMISSION_GRANTED)
        }
        return result
    }

    override fun initViewAndData() {
        initListView()
    }

    private fun initListView() {
        listView = findViewById(R.id.listView)
        mAdapter = VideoListAdapter()
        listView.adapter = mAdapter
        MediaFileScanTask.onDataSetChange = {
            Log.i(TAG, "$it")
            dataMap[simpleDateFormat.format(it.createdAt)]?.apply {
                add(it)
            } ?: kotlin.run {
                val data: CopyOnWriteArrayList<VideoItem> = CopyOnWriteArrayList()
                data.add(it)
                dataMap[simpleDateFormat.format(it.createdAt)] = data
            }
            mAdapter.clear()
            dataMap.forEach {
                mAdapter.add(TitleType(it.key))
                mAdapter.add(ContentType(it.value))
            }
            mAdapter.notifyDataSetChanged()
        }
        MediaFileScanTask.videoList.forEach {
            Log.i(TAG, "$it")
            dataMap[simpleDateFormat.format(it.createdAt)]?.apply {
                add(it)
            } ?: kotlin.run {
                val data: CopyOnWriteArrayList<VideoItem> = CopyOnWriteArrayList()
                data.add(it)
                dataMap[simpleDateFormat.format(it.createdAt)] = data
            }
        }
        dataMap.forEach {
            mAdapter.add(TitleType(it.key))
            mAdapter.add(ContentType(it.value))
        }
        mAdapter.notifyDataSetChanged()
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