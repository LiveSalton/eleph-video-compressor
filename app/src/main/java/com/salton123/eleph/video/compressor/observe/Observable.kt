package com.salton123.eleph.video.compressor.observe

/**
 * Time:2022/2/18 2:43 下午
 * Author:
 * Description:
 */
interface Observable<T> {
    fun observe(data: T)
}