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
const val TABLE_NAME = "VideoInfo"

class VideoSql(context: Context) : SQLiteOpenHelper(context, "video_info.db", null, 1) {
    private val tag = "VideoSql"

    //    CREATE TABLE "video_info" ( "filePath" TEXT PRIMARY KEY, "compressProgress" INTEGER ,"createdAt" INTEGER ,"dirName" TEXT ,"duration" INTEGER  ,"height" INTEGER ,"letter" TEXT ,"mimeType" TEXT ,"name" TEXT ,"playedPosition" INTEGER ,"size" INTEGER ,"updatedAt" INTEGER ,"width" INTEGER  );
    override fun onCreate(db: SQLiteDatabase?) {
        setWriteAheadLoggingEnabled(true)
        db?.apply {
//            enableWriteAheadLogging()
            execSQL("create table ${TABLE_NAME}(" +
                "filePath TEXT PRIMARY KEY," +
                "name varchar(128)," +
                "mimeType varchar(20)," +
                "size INTEGER," +
                "width INTEGER," +
                "height INTEGER," +
                "createdAt INTEGER," +
                "dateTime INTEGER," +
                "squeezeProgress INTEGER," +
                "squeezeState INTEGER," +
                "squeezeSavePath TEXT," +
                "squeezeSize INTEGER," +
                "duration INTEGER" +
                ")")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//        db?.let {
//            val cursor = it.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name<>'sqlite_sequence'", null)
//            cursor.use { cursor ->
//                while (cursor.moveToNext()) {
//                    try {
//                        val tableName = cursor.getString(0)
//                        it.execSQL("DROP TABLE $tableName")
//                    } catch (e: Throwable) {
//                        Log.e(tag, e.message)
//                    }
//                }
//            }
//        }
        db?.execSQL("drop table if exists $TABLE_NAME")
        onCreate(db)
    }


}