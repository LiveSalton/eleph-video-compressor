package com.salton123.eleph.video.kt

import android.util.Log
import android.widget.Toast
import com.salton123.corelite.BuildConfig
import org.xutils.x

/**
 * Time:2022/1/29 5:18 上午
 * Author:
 * Description:
 */
class Ext {
}

fun Any.executeByCached(task: () -> Unit) {
    x.task().run {
        task.invoke()
    }
//    ThreadUtils.executeByCached(object : SimpleTaskCallback() {
//        override fun doInBackground() {
//            task.invoke()
//        }
//    })
}

fun Any.executeByIo(task: () -> Unit) {
    x.task().run {
        task.invoke()
    }
//    ThreadUtils.executeByIo(object : SimpleTaskCallback() {
//        override fun doInBackground() {
//            task.invoke()
//        }
//    })
}

fun Any.runOnUi(task: () -> Unit) {
    x.task().post {
        task.invoke()
    }
}

fun Any.log(msg: String) {
    if (BuildConfig.APP_DEVELOP) {
        Log.i("eleph-compressor", msg)
    }
}

fun Any.toast(msg: String) {
    x.task().post {
        Toast.makeText(x.app(), msg, Toast.LENGTH_SHORT).show()
    }
}