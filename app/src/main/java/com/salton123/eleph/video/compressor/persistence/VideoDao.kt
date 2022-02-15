package com.salton123.eleph.video.compressor.persistence

import com.salton123.eleph.video.compressor.model.VideoItem
import kt.log
import org.xutils.x

/**
 * Time:2022/1/27 14:44
 * Author:wujinsheng1
 * Description:
 */
object VideoDao {
    private val TAG = "VideoDao"

    //    private val videoConfig: DaoConfig = DaoConfig()
//        .setDbName("video.db")
//        .setDbVersion(1)
//        .setDbOpenListener { db -> db.database.enableWriteAheadLogging() }
//        .setDbUpgradeListener { db, oldVersion, newVersion ->
//            try {
//                db.dropDb() // 默认删除所有表
//            } catch (ex: DbException) {
//                LogUtil.e(ex.message, ex)
//            }
//        }
//
//    private val dbManager = x.getDb(videoConfig)
    private val videoInfoDaoImpl: VideoInfoDaoImpl = VideoInfoDaoImpl(x.app())
    fun addVideo(item: VideoItem) {
        log("addVideo:$item")
        videoInfoDaoImpl.add(item)
    }

    fun deleteVideo(item: VideoItem) {
        log("deleteVideo:$item")
        videoInfoDaoImpl.remove(item)
    }

    fun updateVideo(item: VideoItem) {
        log("updateVideo:$item")
        videoInfoDaoImpl.update(item)
    }

    fun findAll(): MutableList<VideoItem>? {
        val ret = videoInfoDaoImpl.getAll()
        log("findAll,${ret.size}")
        return ret
    }
}