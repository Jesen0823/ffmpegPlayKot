package com.jesen.code.ffmpegplaykot.filter

import com.jesen.code.ffmpegplaykot.databean.MediaData
import com.jesen.code.ffmpegplaykot.filter.basefilter.FilePathFilter

class FilterManager {

    private val filters = ArrayList<IFilter<MutableList<MediaData>>>()

    fun  addFilter(filter: IFilter<MutableList<MediaData>>){
        filters.add(filter)
    }

    fun doFilter(list:MutableList<MediaData>){
        for (filter:IFilter<MutableList<MediaData>> in filters){
            filter.doFilter(list)
        }
    }
}