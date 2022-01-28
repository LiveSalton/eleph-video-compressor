package com.salton123.eleph.video.compressor.task

import android.os.Environment
import android.util.Log
import com.salton123.eleph.video.compressor.model.VideoItem
import com.salton123.eleph.video.compressor.persistence.VideoDao
import com.salton123.eleph.video.compressor.utils.Utils
import com.salton123.eleph.video.kt.executeByCached
import com.salton123.eleph.video.kt.executeByIo
import com.salton123.eleph.video.kt.log
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
        log("launch")
        executeByIo {
            log("exec find task")
            File(pathName).listFiles()?.forEach { stubFile ->
                if (stubFile.isFile) {
                    assembleVideoInfo(stubFile)
                } else if (stubFile.isDirectory) {
                    executeByCached {
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
                log("find media file:$filePath")
                videoList.find { it.filePath == filePath }?.let {
                    log("find the same item:${filePath}")
                    if (!File(filePath).exists()) {
                        executeByIo {
                            VideoDao.deleteVideo(it)
                        }
                    }
                } ?: kotlin.run {
                    log("addVideo:$filePath")
                    val videoItem = Utils.retrieveFile(file)
                    VideoDao.addVideo(videoItem)
                }
            } else {
                log("other file:${filePath}")
            }
        } catch (ex: Throwable) {
            ex.printStackTrace()
        }
    }
}