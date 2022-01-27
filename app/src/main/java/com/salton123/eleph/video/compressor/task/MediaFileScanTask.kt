package com.salton123.eleph.video.compressor.task

import android.os.Environment
import android.util.Log
import com.salton123.corelite.BuildConfig
import com.salton123.eleph.video.compressor.model.VideoItem
import com.salton123.eleph.video.compressor.persistence.VideoDao
import com.salton123.eleph.video.compressor.utils.Utils
import org.xutils.x
import java.io.File

/**
 * Time:2021/8/24 6:13 下午
 * Author:
 * Description:
 */
object MediaFileScanTask {
    private val TAG = "MediaFileScanTask"
    private val pathName = Environment.getExternalStorageDirectory().absolutePath
    private val videoList: MutableList<VideoItem> = mutableListOf()
    fun launch() {
        x.task().run {
            File(pathName).listFiles()?.forEach { stubFile ->
                if (stubFile.isFile) {
                    assembleVideoInfo(stubFile)
                } else if (stubFile.isDirectory) {
                    x.task().run {
                        stubFile.walkTopDown().maxDepth(Int.MAX_VALUE).forEach { file ->
                            assembleVideoInfo(file)
                        }
                    }
                }
            }
        }
    }

    /**
     * 从file中装配视频文件信息
     */
    private fun assembleVideoInfo(file: File) {
        try {
            val filePath = file.absolutePath
            if (Utils.filterVideoBySuffix(file)) {
                videoList.find { it.filePath == filePath }?.let {
                    Log.i(TAG, "find the same item:${filePath}")
                    if (!File(filePath).exists()) {
                        x.task().run {
                            VideoDao.deleteVideo(it)
                        }
                    }
                } ?: kotlin.run {
                    val videoItem = Utils.retrieveFile(file)
                    VideoDao.addVideo(videoItem)
                }
            } else {
                if (BuildConfig.APP_DEVELOP) {
                    Log.d(TAG, "assembleVideoInfo:${filePath}")
                }
            }
        } catch (ex: Throwable) {
            ex.printStackTrace()
        }
    }
}