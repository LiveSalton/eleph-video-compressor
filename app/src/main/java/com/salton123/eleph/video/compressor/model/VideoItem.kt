package com.salton123.eleph.video.compressor.model

import android.text.format.Formatter
import com.salton123.eleph.video.compressor.observe.Observable
import com.salton123.eleph.video.compressor.utils.Utils
import org.xutils.x
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
    var duration: Long = 0,
    var slimSize: Long = 0
) : Serializable {

    fun originDensity(): Pair<String, String> {
        return Pair("${width}x${height}", "iw:ih")
    }

    fun threeQuarterDensity(): Pair<String, String> {
        var height = (height * 3 / 4) - (height * 3 / 4) % 2
        var width = (width * 3 / 4) - (width * 3 / 4) % 2
        return Pair("${width}x${height}", "iw*.75:ih*.75")
    }

    fun halfDensity(): Pair<String, String> {
        var height = (height / 2) - (height / 2) % 2
        var width = (width / 2) - (width / 2) % 2
        return Pair("${width}x${height}", "iw*.5:ih*.5")
    }

    fun isSqueezeSuccess(): Boolean {
        return squeezeState == 2
    }

    fun sizeOfStr(): String {
        return Formatter.formatFileSize(x.app(), size)
    }

    fun durationOfStr(): String {
        return Utils.formatTime(duration)
    }
}