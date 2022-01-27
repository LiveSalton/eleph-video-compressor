package com.salton123.eleph.video.compressor.model

/**
 * Time:2022/1/27 11:23
 * Author:
 * Description:
 */
const val TYPE_TITLE = 100
const val TYPE_CONTENT = 101

interface IMultiType {
    val type: Int
}

class TitleType(val title: String) : IMultiType {
    override val type: Int
        get() = TYPE_TITLE
}

class ContentType(val data: MutableList<VideoItem>) : IMultiType {
    override val type: Int
        get() = TYPE_CONTENT
}