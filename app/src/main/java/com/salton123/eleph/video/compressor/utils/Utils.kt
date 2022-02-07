package com.salton123.eleph.video.compressor.utils

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.text.TextUtils
import android.util.Log
import com.salton123.eleph.video.compressor.model.VideoItem
import com.salton123.eleph.video.kt.log
import org.xutils.x
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * User: wujinsheng1@yy.com
 * Date: 2021/9/4 23:32
 * ModifyTime: 23:32
 * Description:
 */
object Utils {
    private const val TAG = "Utils"
    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    fun getDateTitle(timeMills: Long): String {
        val title = simpleDateFormat.format(timeMills)
        return title
    }

    fun getDateTime(timeMills: Long): Long {
        try {
            return timeMills - timeMills % (24 * 60 * 60 * 1000)
//            return simpleDateFormat.parse(simpleDateFormat.format(timeMills)).time
        } catch (ex: Exception) {
            ex.printStackTrace()
            log("getDateTime,timeMills:$timeMills")
            return 0L
        }
    }

    /**
     * 将毫秒数格式化为"##:##"的时间
     * @param milliseconds 毫秒数
     * @return ##:##
     */
    fun formatTime(milliseconds: Long): String {
        if (milliseconds <= 0 || milliseconds >= 24 * 60 * 60 * 1000) {
            return "00:00"
        }
        val totalSeconds = milliseconds / 1000
        val seconds = totalSeconds % 60
        val minutes = totalSeconds / 60 % 60
        val hours = totalSeconds / 3600
        val stringBuilder = StringBuilder()
        val mFormatter = java.util.Formatter(stringBuilder, Locale.getDefault())
        return if (hours > 0) {
            mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString()
        } else {
            mFormatter.format("%02d:%02d", minutes, seconds).toString()
        }
    }

    val videoSuffix = arrayOf("mp4", "mpeg", "mpg", "m4v", "mov", "mkv", "webm", "ts", "avi")
    fun filterVideoBySuffix(file: File): Boolean {
        return if (file.isFile) {
            return videoSuffix.contains(file.name.substringAfter("."))
        } else {
            false
        }
    }

    fun retrieveFile(file: File): VideoItem {
        val mmr = MediaMetadataRetriever()
        val filePath = file.absolutePath
        Log.i(TAG, "filePath:$filePath")
        mmr.setDataSource(filePath)
        val duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)?.toLongOrNull() ?: 0
        val name = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
        val mimeType = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE)
        val width = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)?.toIntOrNull() ?: 0
        val height = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)?.toIntOrNull() ?: 0
//        saveBitmap(filePath, mmr.frameAtTime)
        mmr.release()
        return VideoItem().apply {
            this.filePath = filePath
            this.name = if (TextUtils.isEmpty(name)) getFileName(filePath) else name!!
            this.dirName = dirName(file)
            this.mimeType = if (TextUtils.isEmpty(mimeType)) "video/mp4" else mimeType
            this.size = file.length()
            this.duration = duration
            this.createdAt = file.lastModified()
            this.updatedAt = System.currentTimeMillis()
            this.width = width
            this.height = height
            this.letter = dirName(file).first().toString()
            this.dateTime = getDateTime(file.lastModified())
        }
    }

    private fun dirName(filePath: String): String {
        val dirList = filePath.split(File.separator)
        val size = dirList.size
        var dirName = "sdcard"
        dirName = when (size) {
            in 0..3 -> {
                "sdcard"
            }
            in 4..6 -> {
                dirList[3]
            }
            in 7..9 -> {
                dirList[5]
            }
            else -> {
                dirList[6]
            }
        }
        return dirName
    }

    fun saveBitmap(filePath: String, bitmap: Bitmap) {
        x.task().post {
            val savePath = getSaveBitmapPath(filePath)
            val savePathFile = File(savePath)
            if (!savePathFile.exists()) {
                savePathFile.mkdirs()
            }
            BitmapUtil.saveImage(savePath, bitmap, Bitmap.CompressFormat.JPEG, 80)
        }
    }

    fun getSaveBitmapPath(filePath: String): String {
        return x.app().cacheDir.absolutePath + File.separator + "cache" + File.separator + Md5Utils.md5(filePath)
    }

    private fun dirName(file: File): String {
        return dirName(file.absolutePath)
    }

    /**
     * Return the name of file.
     *
     * @param filePath The path of file.
     * @return the name of file
     */
    fun getFileName(filePath: String): String {
        if (TextUtils.isEmpty(filePath)) return ""
        val lastSep = filePath.lastIndexOf(File.separator)
        return if (lastSep == -1) filePath else filePath.substring(lastSep + 1)
    }

    /**
     * 重命名文件和文件夹
     *
     * @param file        File对象
     * @param newFileName 新的文件名
     * @return 执行结果
     */
    fun renameFile(file: File, newFileName: String): Boolean {
        var newFile = if (file.isDirectory) {
            File(file.parentFile, newFileName)
        } else {
            val temp = (newFileName
                + file.name.substring(
                file.name.lastIndexOf('.')))
            File(file.parentFile, temp)
        }
        println("file:${file.absolutePath},newFile:${newFile.absolutePath}")
        if (file.renameTo(newFile)) {
            return true
        }
        return false
    }

    fun isFileExist(filePath: String): Boolean {
        return File(filePath).exists()
    }
}