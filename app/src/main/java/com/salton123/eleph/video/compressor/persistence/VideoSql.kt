package com.salton123.eleph.video.compressor.persistence

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Time:2022/1/29 6:01 上午
 * Author:
 * Description:
 */
class VideoSql(context: Context) : SQLiteOpenHelper(context, "video_info.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table VideoInfo(" +
                "id integer primary key autoincrement," +
                "filePath text," +
                "dirName text)")
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}