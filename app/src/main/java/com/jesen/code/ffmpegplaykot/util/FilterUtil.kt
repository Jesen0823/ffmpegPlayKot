package com.jesen.code.ffmpegplaykot.util

import com.jesen.code.ffmpegplaykot.filter.FilterManager
import com.jesen.code.ffmpegplaykot.filter.basefilter.FilePathFilter
import com.jesen.code.ffmpegplaykot.filter.picturefilter.PicPathFilter
import com.jesen.code.ffmpegplaykot.filter.picturefilter.PicSizeFilter
import com.jesen.code.ffmpegplaykot.filter.videofilter.VideoDurationFilter
import com.jesen.code.ffmpegplaykot.filter.videofilter.VideoPreviewFilter
import com.jesen.code.ffmpegplaykot.filter.videofilter.VideoTypeFilter

object FilterUtil {

    fun getDefaultPicFilterManager(): FilterManager{
        val filterManager = FilterManager()
        filterManager.addFilter(FilePathFilter())
        filterManager.addFilter(PicPathFilter())
        filterManager.addFilter(PicSizeFilter())
        return filterManager
    }

    fun getDefaultVideoFilterManager(): FilterManager {
        val filterManager = FilterManager()
        filterManager.addFilter(FilePathFilter())
        filterManager.addFilter(VideoTypeFilter())
        filterManager.addFilter(VideoDurationFilter())
        filterManager.addFilter(VideoPreviewFilter())
        return filterManager
    }
}