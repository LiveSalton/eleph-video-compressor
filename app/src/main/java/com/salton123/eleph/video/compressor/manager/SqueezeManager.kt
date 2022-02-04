package com.salton123.eleph.video.compressor.manager

import com.salton123.eleph.video.compressor.model.VideoItem
import com.salton123.eleph.video.compressor.task.FFmpegCompressor
import java.util.concurrent.LinkedBlockingQueue

/**
 * Time:2022/2/3 2:35 下午
 * Author:
 * Description:
 */
object SqueezeManager {
    private val squeezeList: MutableList<((Int) -> Unit)> = mutableListOf()
    private val taskQueue: LinkedBlockingQueue<VideoItem> = LinkedBlockingQueue(10)
    fun add(videoItem: VideoItem) {
        taskQueue.add(videoItem)
        if (taskQueue.isNotEmpty()) {
            FFmpegCompressor.compress(videoItem.filePath) {
                squeezeList.forEach { item ->
                    item.invoke(it)
                }
            }
        }
    }

    fun remove(callback: ((Int) -> Unit)) {
        squeezeList.remove(callback)
    }
}