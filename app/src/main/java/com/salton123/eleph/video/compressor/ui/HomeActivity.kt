package com.salton123.eleph.video.compressor.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Process
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.salton123.base.BaseActivity
import com.salton123.base.feature.ImmersionFeature
import com.salton123.eleph.R
import com.salton123.eleph.video.compressor.adapter.VideoRecyclerAdapter
import com.salton123.eleph.video.compressor.model.ContentStubType
import com.salton123.eleph.video.compressor.model.IMultiType
import com.salton123.eleph.video.compressor.model.TYPE_TITLE
import com.salton123.eleph.video.compressor.model.TitleType
import com.salton123.eleph.video.compressor.task.MediaFileScanTask
import com.salton123.eleph.video.kt.runOnUi

/**
 * Time:2022/1/29 11:40 上午
 * Author:
 * Description:
 */

class HomeActivity : BaseActivity() {
    private val TAG = "HomeActivity"
    private val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private val REQUEST_CODE = 101
    override fun getLayout(): Int = R.layout.activity_home
    private lateinit var mImmersionFeature: ImmersionFeature
    private lateinit var recyclerView: RecyclerView
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
    }

    private val dataList: MutableList<IMultiType> = mutableListOf()
    private fun initListView() {
        recyclerView = findViewById(R.id.recyclerView)
        mAdapter = VideoRecyclerAdapter()
        recyclerView.adapter = mAdapter
        GridLayoutManager(this, 4).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (mAdapter.getItemViewType(position) == TYPE_TITLE) {
                        4
                    } else {
                        2
                    }
                }
            }
            recyclerView.layoutManager = this
        }
        MediaFileScanTask.onDataSetChange = { map ->
            runOnUi {
                dataList.clear()
                map.forEach {
                    dataList.add(TitleType(it.key))
                    it.value.forEach { item ->
                        dataList.add(ContentStubType(item))
                    }
                }
                mAdapter.setData(dataList)
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