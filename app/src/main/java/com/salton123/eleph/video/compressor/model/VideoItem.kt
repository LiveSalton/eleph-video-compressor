package com.salton123.eleph.video.compressor.model

import java.io.Serializable

/**
 * Time:2022/2/14 10:03
 * Author:
 * Description:
 */
data class VideoItem(
    var filePath: String = "",
    var name: String = "",
    var mimeType: String = "",
    var size: Long = 0,
    var width: Int = 0,
    var height: Int = 0,
    var createdAt: Long = 0,
    var dateTime: Long = 0,
    var squeezeProgress: Int = 0,
    var squeezeState: Int = 0,  //0 default 1 ing 2 success 3 failed
    var squeezeSavePath: String = "",
    var squeezeSize: Long = 0,
    var duration: Long = 0
) : Serializable {

    fun originDensity(): String {
        return "${width}x${height}"
    }

    fun threeQuarterDensity(): String {
        var height = (height * 4 / 3) - (height * 4 / 3) % 2
        var width = (width * 4 / 3) - (width * 4 / 3) % 2
        return "${width}x${height}"
    }

    fun halfDensity(): String {
        var height = (height / 2) - (height / 2) % 2
        var width = (width / 2) - (width / 2) % 2
        return "${width}x${height}"
    }

    fun isSqueezeSuccess(): Boolean {
        return squeezeState == 2
    }
}