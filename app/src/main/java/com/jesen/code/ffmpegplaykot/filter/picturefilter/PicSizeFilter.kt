package com.jesen.code.ffmpegplaykot.filter.picturefilter

import android.graphics.BitmapFactory
import com.jesen.code.ffmpegplaykot.databean.MediaData
import com.jesen.code.ffmpegplaykot.filter.IFilter

class PicSizeFilter():IFilter<MutableList<MediaData>> {

    override fun doFilter(t: MutableList<MediaData>) {
        val iterator = t.iterator()
        while (iterator.hasNext()){
            val mediaData :MediaData = iterator.next()
            val options = BitmapFactory.Options()
            BitmapFactory.decodeFile(mediaData.filePath, options)
            if (options.outWidth <= 24 || options.outHeight <= 24){
                iterator.remove()
            }
        }
    }

}