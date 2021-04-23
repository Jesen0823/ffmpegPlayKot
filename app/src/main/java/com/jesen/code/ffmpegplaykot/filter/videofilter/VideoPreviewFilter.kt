package com.jesen.code.ffmpegplaykot.filter.videofilter

import android.graphics.BitmapFactory
import com.jesen.code.ffmpegplaykot.databean.MediaData
import com.jesen.code.ffmpegplaykot.filter.IFilter

class VideoPreviewFilter : IFilter<MutableList<MediaData>> {

    override fun doFilter(t: MutableList<MediaData>) {
        val iterator = t.iterator()
        while (iterator.hasNext()) {
            val mediaData: MediaData = iterator.next()
            val options = BitmapFactory.Options()
            BitmapFactory.decodeFile(mediaData.previewPath, options)
            if (options.outWidth <= 0 || options.outHeight <= 0) {
                iterator.remove()
            }
        }
    }
}