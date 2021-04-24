package com.jesen.code.ffmpegplaykot.util

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import com.jesen.code.ffmpegplaykot.view.activity.PickerActivity
import android.os.Handler
import android.os.Message
import com.jesen.code.ffmpegplaykot.databean.MediaData
import com.jesen.code.ffmpegplaykot.databean.MediaType

object PicScanHelper {

    fun start(context: Context, handler: Handler){
        Thread{
            doScan(context, handler)
        }.start()
    }

    private fun doScan(context: Context, handler: Handler){
        val mediaColumns:Array<String> = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.ORIENTATION,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media.MIME_TYPE,
            //MediaStore.Images.Media.SIZE,
            MediaStore.Images.Media.LATITUDE,
            MediaStore.Images.Media.LONGITUDE
        )

        val sortOrder: String = MediaStore.Images.Media.DATE_ADDED + " desc";
        var cursor: Cursor? = null
        try {
            cursor = context.contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, mediaColumns, null, null, sortOrder);

            cursor?.let {
                if (it.moveToFirst()) {
                    parseData(cursor, handler)
                }
            }
        } finally {
            cursor?.close()
        }
    }

    private fun parseData(cursor: Cursor, handler: Handler){
        val imageList: MutableList<MediaData> = ArrayList()

        do {
            val id = ScanResultUtil.getLongResultByKey(cursor, MediaStore.Images.Media._ID)
            val createTime = ScanResultUtil.getLongResultByKey(cursor, MediaStore.Images.Media.DATE_ADDED)
            val filePath = ScanResultUtil.getStringResultByKey(cursor, MediaStore.Images.Media.DATA)
            val mimeType = ScanResultUtil.getStringResultByKey(cursor, MediaStore.Images.Media.MIME_TYPE)
            //val fileSize = ScanResultUtil.getStringResultByKey(cursor,MediaStore.Images.Media.SIZE)
            val latitude = ScanResultUtil.getFloatResultByKey(cursor, MediaStore.Images.Media.LATITUDE)
            val longitude = ScanResultUtil.getFloatResultByKey(cursor, MediaStore.Images.Media.LONGITUDE)
            val albumName = ScanResultUtil.getAlbumNameFromPath(filePath)
            imageList.add(MediaData(id, createTime, null, albumName, filePath, null, mimeType, latitude, longitude))
        } while (cursor.moveToNext())

        val msg = Message()
        msg.obj = imageList
        msg.what = MediaType.MEDIA_TYPE_PICTURE
        handler.sendMessage(msg)
    }
}