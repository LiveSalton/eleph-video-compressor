package com.salton123.eleph.video.compressor.utils

import android.util.Log

/**
 * Time:2021/10/20 17:54
 * Author:
 * Description:
 */
open class SimpleTaskCallback() : ThreadUtils.Task<Unit>() {
    private val tag = "SimpleTaskCallback"
    override fun doInBackground() {
//        XLog.i(tag, "doInBackground")
    }

    override fun onSuccess(result: Unit?) {
//        XLog.i(tag, "onSuccess")
    }

    override fun onCancel() {
//        XLog.i(tag, "onCancel")
    }

    override fun onFail(t: Throwable?) {
        Log.i(tag, "onFail:$t")
    }
}