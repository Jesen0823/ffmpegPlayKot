package com.jesen.code.ffmpegplaykot.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.jesen.code.ffmpegplaykot.databean.MediaPrev
import com.jesen.code.ffmpegplaykot.view.fragment.PictureFragment
import com.jesen.code.ffmpegplaykot.view.fragment.VideoFragment

class FragAdapter(fragmentManager: FragmentManager):FragmentStatePagerAdapter(fragmentManager){

    private var mPreviewDataPathList: MutableList<MediaPrev> = ArrayList()

    constructor(fragmentManager: FragmentManager, list: MutableList<MediaPrev>?):this(fragmentManager){
        list?.let {
            mPreviewDataPathList.addAll(list)
        }
    }

    override fun getItem(position: Int): Fragment {
        val mediaData:MediaPrev = mPreviewDataPathList[position]

        mediaData.takeIf {
            it.isVideo()
        }?.let {
            return VideoFragment.newInstance(it)
        }

        mediaData.takeIf {
            it.isPicture()
        }?.let {
            return PictureFragment.newInstance(it)
        }
        return Fragment()
    }

    override fun getCount(): Int {
        return mPreviewDataPathList.size
    }

}