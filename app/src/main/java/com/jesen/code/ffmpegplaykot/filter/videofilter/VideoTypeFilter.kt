package com.jesen.code.ffmpegplaykot.filter.videofilter

import com.jesen.code.ffmpegplaykot.databean.MediaData
import com.jesen.code.ffmpegplaykot.filter.IFilter

class VideoTypeFilter:IFilter<MutableList<MediaData>> {

    override fun doFilter(t: MutableList<MediaData>) {
        val iterator = t.iterator()
        while (iterator.hasNext()){
            val mediaData = iterator.next()
            (mediaData.filePath?.endsWith(".ts")!!
                    || mediaData.filePath?.endsWith(".m3u8")!!)
                ?.let {
                when(it){
                    true -> iterator.remove()
                }
            }
        }
    }
}