package com.jesen.code.ffmpegplaykot.filter.picturefilter

import com.jesen.code.ffmpegplaykot.databean.MediaData
import com.jesen.code.ffmpegplaykot.filter.IFilter

class PicPathFilter():IFilter<MutableList<MediaData>> {

    override fun doFilter(t: MutableList<MediaData>) {
        val iterator = t.iterator()

        while (iterator.hasNext()){
            val mediaData:MediaData = iterator.next()

            mediaData.filePath?.contains("weixin").let {
                when(it){
                    true -> iterator.remove()
                }
            }
        }
    }
}