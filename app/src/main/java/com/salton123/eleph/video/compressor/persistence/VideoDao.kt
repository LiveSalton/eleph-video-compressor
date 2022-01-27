package com.salton123.eleph.video.compressor.persistence

import com.salton123.eleph.video.compressor.model.VideoItem
import org.xutils.DbManager.DaoConfig
import org.xutils.common.util.LogUtil
import org.xutils.ex.DbException
import org.xutils.x

/**
 * Time:2022/1/27 14:44
 * Author:wujinsheng1
 * Description:
 */
object VideoDao {
    private val videoConfig: DaoConfig = DaoConfig()
        .setDbName("video.db")
        .setDbVersion(1)
        .setDbOpenListener { db -> db.database.enableWriteAheadLogging() }
        .setDbUpgradeListener { db, oldVersion, newVersion ->
            try {
                db.dropDb() // 默认删除所有表
            } catch (ex: DbException) {
                LogUtil.e(ex.message, ex)
            }
        }

    private val dbManager = x.getDb(videoConfig)
    fun addVideo(item: VideoItem) {
        dbManager.save(item)
    }

    fun deleteVideo(item: VideoItem) {
        dbManager.delete(item)
    }

    fun updateVideo(item: VideoItem) {
        dbManager.update(item)
    }

    fun findAll(): MutableList<VideoItem>? {
        val ret = x.getDb(videoConfig).findAll(VideoItem::class.java)
        return ret
    }
}