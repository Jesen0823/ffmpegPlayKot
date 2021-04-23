package com.jesen.code.ffmpegplaykot.filter.basefilter

import com.jesen.code.ffmpegplaykot.databean.MediaData
import com.jesen.code.ffmpegplaykot.filter.IFilter
import java.io.File

class FilePathFilter:IFilter<MutableList<MediaData>> {

    override fun doFilter(list: MutableList<MediaData>) {
        val iterator = list.iterator()
        while (iterator.hasNext()){
            val mediaData = iterator.next();
            if (mediaData.filePath.isNullOrEmpty()){
                iterator.remove()
                continue
            }

            val file = File(mediaData.filePath)
            if (!file.exists()){
                iterator.remove()
            }
        }
    }
}