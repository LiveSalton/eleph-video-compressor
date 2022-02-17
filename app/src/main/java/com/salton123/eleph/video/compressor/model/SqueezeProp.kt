package com.salton123.eleph.video.compressor.model

import kt.log
import java.io.File

/**
 * Time:2022/2/3 8:51 下午
 * Author:
 * Description:
 */
data class SqueezeProp(
    var filePath: String,
    var vcodec: String = "h264",
    var acodec: String = "aac",
    var scale: Pair<String, String> = Pair("", "")
) {
    fun toFFmpegProp(): String {
        val savePath = getSavePath()
        val processors = Runtime.getRuntime().availableProcessors() / 2
        val prop = "-i $filePath -vcodec $vcodec -acodec $acodec -vf scale=${scale.second} -y -threads $processors $savePath"
        log("savePath:$savePath,prop:$prop")
        return prop
    }

    fun getSavePath(): String {
        val file = File(filePath)
        val fileName = file.name.replace("${file.extension}", "_${vcodec}_${acodec}_${scale.first}_squeeze.${file.extension}")
        return file.parent + File.separator + fileName
    }
}
