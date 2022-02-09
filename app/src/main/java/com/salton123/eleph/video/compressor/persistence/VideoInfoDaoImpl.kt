package com.salton123.eleph.video.compressor.persistence

import android.content.ContentValues
import android.content.Context
import com.salton123.eleph.video.compressor.model.VideoItem

/**
 * Time:2022/2/5 9:43 下午
 * Author:
 * Description:
 */
class VideoInfoDaoImpl(context: Context) : VideoInfoDao {
    private val sql = VideoSql(context)
    val table = "video"
    override fun add(item: VideoItem) {
        ContentValues().apply {
            put("filePath", item.filePath)
            put("name", item.name)
            put("mimeType", item.mimeType)
            put("size", item.size)
            put("width", item.width)
            put("height", item.height)
            put("createdAt", item.createdAt)
            put("dateTime", item.dateTime)
            put("squeezeProgress", item.squeezeProgress)
            put("squeezeState", item.squeezeState)
            sql.writableDatabase.also {
                it.insert(table, null, this)
                it.close()
            }
        }
    }

    override fun remove(item: VideoItem) {
        sql.writableDatabase.also {
            it.delete(table, "filePath=?", arrayOf("${item.filePath}"))
            it.close()
        }
    }

    override fun update(item: VideoItem) {
        ContentValues().apply {
            put("filePath", item.filePath)
            put("name", item.name)
            put("mimeType", item.mimeType)
            put("size", item.size)
            put("width", item.width)
            put("height", item.height)
            put("createdAt", item.createdAt)
            put("dateTime", item.dateTime)
            put("squeezeProgress", item.squeezeProgress)
            put("squeezeState", item.squeezeState)
            sql.writableDatabase.also {
                it.update(table, this, "id=?", arrayOf("${item.id}"))
                it.close()
            }
        }
    }

    override fun getItem(filePath: String): VideoItem? {
        sql.readableDatabase.also {
            var videoItem: VideoItem? = null
            val cursor = it.query(table, null, "filePath=?", arrayOf(filePath), null, null, null)
            if (cursor.moveToNext()) {
                videoItem = VideoItem()
                videoItem.id = cursor.getInt(0)
                videoItem.filePath = cursor.getString(1)
                videoItem.name = cursor.getString(2)
                videoItem.mimeType = cursor.getString(3)
                videoItem.size = cursor.getLong(4)
                videoItem.width = cursor.getInt(5)
                videoItem.height = cursor.getInt(6)
                videoItem.createdAt = cursor.getLong(7)
                videoItem.dateTime = cursor.getLong(8)
                videoItem.squeezeProgress = cursor.getInt(9)
                videoItem.squeezeState = cursor.getInt(10)
            }
            it.close()
            return videoItem
        }
    }

    override fun getAll(): MutableList<VideoItem> {
        sql.readableDatabase.also {
            val cursor = it.rawQuery("select * from $table", null)
            val dataList: MutableList<VideoItem> = mutableListOf()
            while (cursor.moveToNext()) {
                var videoItem = VideoItem()
                videoItem.id = cursor.getInt(0)
                videoItem.filePath = cursor.getString(1)
                videoItem.name = cursor.getString(2)
                videoItem.mimeType = cursor.getString(3)
                videoItem.size = cursor.getLong(4)
                videoItem.width = cursor.getInt(5)
                videoItem.height = cursor.getInt(6)
                videoItem.createdAt = cursor.getLong(7)
                videoItem.dateTime = cursor.getLong(8)
                videoItem.squeezeProgress = cursor.getInt(9)
                videoItem.squeezeState = cursor.getInt(10)
                dataList.add(videoItem)
            }
            it.close()
            return dataList
        }
    }
}