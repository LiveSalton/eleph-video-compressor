package com.salton123.eleph.video.compressor.observe

import com.salton123.eleph.video.compressor.model.VideoItem
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.properties.Delegates

/**
 * Time:2022/2/18 2:38 下午
 * Author:
 * Description:
 */
//TODO 先不用观察者模式
object VideoItemObserver {
    private var observers: CopyOnWriteArrayList<Observable<VideoItem>> = CopyOnWriteArrayList()
    private var toUpdate: VideoItem by Delegates.observable(VideoItem()) { _, old, new ->
        observers.forEach {
            it.observe(new)
        }
    }

    fun add(item: Observable<VideoItem>) {
        if (!observers.contains(item)) {
            this.observers.add(item)
        }
    }

    fun remove(item: Observable<VideoItem>) {
        this.observers.remove(item)
    }

    fun observe(item: VideoItem) {
        this.toUpdate = item
    }
}