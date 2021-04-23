package com.jesen.code.ffmpegplaykot.util

import android.content.Context
import android.provider.MediaStore
import com.jesen.code.ffmpegplaykot.view.activity.PickerActivity
import android.os.Handler

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
            MediaStore.Images.Media.LATITUDE,
            MediaStore.Images.Media.LONGITUDE
        )
    }
}