package com.salton123.eleph.video.compressor.persistence

import com.salton123.eleph.video.compressor.model.VideoItem

/**
 * Time:2022/2/5 9:41 下午
 * Author:
 * Description:
 */
interface VideoInfoDao {
    fun add(item: VideoItem)
    fun remove(item: VideoItem)
    fun update(item: VideoItem)
    fun getItem(filePath: String): VideoItem?
    fun getAll(): MutableList<VideoItem>
}