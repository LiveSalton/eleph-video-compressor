package com.salton123.eleph.video.compressor.persistence

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

/**
 * Time:2022/1/29 6:01 上午
 * Author:
 * Description:
 */
class VideoSql(context: Context) : SQLiteOpenHelper(context, "video_info.db", null, 1) {
    private val tag = "VideoSql"
    override fun onCreate(db: SQLiteDatabase?) {
        db?.apply {
            enableWriteAheadLogging()
            execSQL("create table VideoInfo(" +
                "id integer primary key autoincrement," +
                "filePath text," +
                "name varchar(128)," +
                "mimeType varchar(20)," +
                "size INTEGER," +
                "width INTEGER," +
                "height INTEGER," +
                "createdAt INTEGER," +
                "dateTime INTEGER," +
                "squeezeProgress INTEGER," +
                "squeezeState INTEGER" +
                ")")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.let {
            val cursor = it.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name<>'sqlite_sequence'", null)
            cursor.use { cursor ->
                while (cursor.moveToNext()) {
                    try {
                        val tableName = cursor.getString(0)
                        it.execSQL("DROP TABLE $tableName")
                    } catch (e: Throwable) {
                        Log.e(tag, e.message)
                    }
                }
            }
        }
    }
}