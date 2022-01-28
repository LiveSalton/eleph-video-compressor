package com.salton123.eleph.video.compressor.model

import com.salton123.eleph.R

/**
 * Time:2022/1/27 11:23
 * Author:
 * Description:
 */
const val TYPE_TITLE = R.layout.view_stub_title
const val TYPE_CONTENT = R.layout.view_stub_content

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