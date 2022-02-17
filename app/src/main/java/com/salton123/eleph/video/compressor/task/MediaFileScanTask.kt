package com.salton123.eleph.video.compressor.task

import android.os.Environment
import com.salton123.eleph.video.compressor.model.VideoItem
import com.salton123.eleph.video.compressor.persistence.VideoDao
import com.salton123.eleph.video.compressor.utils.Utils
import kt.executeByCached
import kt.executeByIo
import kt.log
import java.io.File
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Time:2021/8/24 6:13 下午
 * Author:
 * Description:
 */
object MediaFileScanTask {
    private val TAG = "MediaFileScanTask"
    private val pathName = Environment.getExternalStorageDirectory().absolutePath
    val videoMap: ConcurrentHashMap<Long, CopyOnWriteArrayList<VideoItem>> = ConcurrentHashMap()
    var onTypeListChange: ((MutableList<Long>) -> Unit)? = null
    var onDataListChange: ((Long, CopyOnWriteArrayList<VideoItem>) -> Unit)? = null
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
        videoMap.clear()
        val cacheAllData = VideoDao.findAll()
        if (cacheAllData?.isEmpty() == true) {
            val emptyList: MutableList<Long> = mutableListOf()
            onTypeListChange?.invoke(emptyList)
        } else {
            cacheAllData?.forEach {
                addVideoToMap(it)
            }
        }

    }

    /**
     * 从file中装配视频文件信息
     */
    private fun assembleVideoInfo(file: File) {
        val filePath = file.absolutePath
        if (Utils.filterVideoBySuffix(file)) {
//            log("find media file:$filePath")
            val title = Utils.getDateTime(file.lastModified())
            videoMap[title]?.apply {
                //在集合中找
                find { it.filePath == filePath }?.let {
//                    log("find the same item:${filePath}")
                    if (!File(filePath).exists()) {
                        executeByIo {
                            VideoDao.deleteVideo(it)
                        }
                    }
                } ?: kotlin.run {
                    addVideo(file)
                }
            } ?: kotlin.run {
                addVideo(file)
            }
        } else {
//            log("other file:${filePath}")
        }
    }

    private fun addVideo(file: File) {
        try {
//            log("addVideo:$file")
            if (fileFilter(file)) {
                val videoItem = Utils.retrieveFile(file)
                addVideoToMap(videoItem)
                VideoDao.addVideo(videoItem)
            }
        } catch (ex: Exception) {
            //
        }
    }

    private fun fileFilter(file: File): Boolean {
        if (file.length() < 1024 * 1024) {
            return false
        }
        if (file.name.contains("_squeeze")) {
            return false
        }
        return true
    }

    private fun addVideoToMap(videoItem: VideoItem) {
        val title = videoItem.dateTime
        videoMap[title]?.apply {
            if (Utils.isFileExist(videoItem.filePath)) {
                add(videoItem)
                onDataListChange?.invoke(title, this)
            } else {
                VideoDao.deleteVideo(videoItem)
            }
        } ?: kotlin.run {
            val list: CopyOnWriteArrayList<VideoItem> = CopyOnWriteArrayList()
            list.add(videoItem)
            videoMap[title] = list
            val types = videoMap.keys.sortedDescending().toMutableList()
            onTypeListChange?.invoke(types)
            onDataListChange?.invoke(title, list)
        }
    }

    fun removeVideoItem(videoItem: VideoItem) {
        val title = videoItem.dateTime
        videoMap[title]?.apply {
            remove(videoItem)
            if (videoMap[title]?.isEmpty() == true) {
                videoMap.remove(title)
                log("remove title:$title")
                val types = videoMap.keys.sortedDescending().toMutableList()
                onTypeListChange?.invoke(types)
            }
        }
    }
}