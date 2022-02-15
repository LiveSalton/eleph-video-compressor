package com.salton123.eleph.video.compressor.task

import android.util.Log
import com.arthenica.ffmpegkit.FFmpegKit
import com.arthenica.ffmpegkit.FFprobeKit
import com.arthenica.ffmpegkit.MediaInformationSession
import com.arthenica.ffmpegkit.ReturnCode
import com.salton123.eleph.video.compressor.model.SqueezeProp
import java.io.File

/**
 * Time:2022/1/19 5:14 下午
 * Author:
 * Description:
 */
object FFmpegCompressor {
    private const val TAG = "FFmpegCompressor"

    fun totalNbFrames(filePath: String): Int {
        var totalFrames = -1
        try {
            val session = FFprobeKit.getMediaInformation(filePath)
            session.mediaInformation.streams.forEach { streamInfo ->
                if ("video" == streamInfo.allProperties.optString("codec_type")) {
                    totalFrames = streamInfo.allProperties.optString("nb_frames").toIntOrNull() ?: 0
                }
            }
        } finally {
            return totalFrames
        }
    }

    fun compress(filePath: String, callback: (Int) -> Unit) {
        val totalFrames = totalNbFrames(filePath)
        val newFilePath = getCompressPath(filePath)
        val processors = Runtime.getRuntime().availableProcessors()
//        ffmpeg -i test.mp4 -vcodec h264 -acodec aac -vf scale=-1:1080 -y out_h264_1080_aac.mp4

        val command = "-vcodec h264 -i $filePath -vcodec h264  -threads $processors $newFilePath"
//        val command = "-vcodec mpeg4 -i $filePath -vcodec mpeg4  -threads $processors $newFilePath"
        FFmpegKit.executeAsync(command, {
            Log.i(TAG, "completeCallback:$it")
            if (it.returnCode.value == ReturnCode.SUCCESS) {
                callback.invoke(100)
            } else {
                callback.invoke(-1)
            }
        }, {
        }, {
            callback.invoke(it.videoFrameNumber * 100 / totalFrames)
        })
    }

    fun squeeze(prop: SqueezeProp, callback: (Int) -> Unit) {
        val totalFrames = totalNbFrames(prop.filePath)
        FFmpegKit.executeAsync(prop.toFFmpegProp(), {
            Log.i(TAG, "completeCallback:$it")
            if (it.returnCode.value == ReturnCode.SUCCESS) {
                callback.invoke(100)
            } else {
                callback.invoke(-1)
            }
        }, {
            Log.i(TAG, "logCallback:$it")
        }, {
            Log.i(TAG, "statisticsCallback:$it")
            callback.invoke(it.videoFrameNumber * 100 / totalFrames)
        })
    }

    open fun getCompressPath(filePath: String): String {
        val file = File(filePath)
        val fileName = file.name.replace(".mp4", "_compress.mp4")
        return file.parent + File.separator + fileName
    }

    fun getVideoInfo(filePath: String?): MediaInformationSession {
        return FFprobeKit.getMediaInformation(filePath)
    }

    fun help() {
        FFmpegKit.executeAsync("-encoders", {
            Log.i(TAG, "help:$it")
        }, {
//            Log.i(TAG, "help:$it")
        }, {
//            Log.i(TAG, "help:$it")
        })

        FFmpegKit.executeAsync("-devices", {
            Log.i(TAG, "help:$it")
        }, {
//            Log.i(TAG, "help:$it")
        }, {
//            Log.i(TAG, "help:$it")
        })
    }
}