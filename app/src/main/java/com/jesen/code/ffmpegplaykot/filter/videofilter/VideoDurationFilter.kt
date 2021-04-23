package com.jesen.code.ffmpegplaykot.filter.videofilter

import com.jesen.code.ffmpegplaykot.databean.MediaData
import com.jesen.code.ffmpegplaykot.filter.IFilter

class VideoDurationFilter :IFilter<MutableList<MediaData>> {

    override fun doFilter(t: MutableList<MediaData>) {
        val iterator = t.iterator()
        while (iterator.hasNext()){
            val mediaData = iterator.next()
            mediaData.duration?.let {
                if (it < 2000){
                    iterator.remove()
                }
            }
        }
    }
}