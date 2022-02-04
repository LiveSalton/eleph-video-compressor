package com.salton123.eleph.video.compressor.model

import com.salton123.eleph.video.kt.log
import org.xutils.x
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
    var scale: String = ""
) {
    fun toFFmpegProp(): String {
        val savePath = getSavePath(filePath)
        val prop = "-i $filePath -vcodec $vcodec -acodec $acodec -vf scale=$scale -y $savePath"
        log("savePath:$savePath,prop:$prop")
        return prop
    }

    fun getSavePath(filePath: String): String {
        val file = File(filePath)
        val fileName = file.name.replace(".${file.extension}", "_${vcodec}_${acodec}_${scale}.${file.extension}")
//        return x.app().externalCacheDir.absolutePath + File.separator + ".cache" + File.separator + fileName
        return x.app().externalCacheDir.absolutePath + File.separator + fileName
    }
}
