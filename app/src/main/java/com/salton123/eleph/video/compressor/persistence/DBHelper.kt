package com.salton123.eleph.video.compressor.persistence

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Time:2022/2/5 9:00 下午
 * Author:
 * Description:
 */
class DBHelper(context: Context) : SQLiteOpenHelper(context, "video.db", null, 1) {
    private var mDatabase: SQLiteDatabase = writableDatabase

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table video(id INTEGER PRIMARY KEY AUTOINCREMENT," + "name varchar(20), address TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}