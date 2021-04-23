package com.jesen.code.ffmpegplaykot.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.jesen.code.ffmpegplaykot.R
import com.jesen.code.ffmpegplaykot.adapter.FragAdapter
import com.jesen.code.ffmpegplaykot.databean.MediaPrev

class PreviewActivity : AppCompatActivity() {
    private lateinit var mAdapter: FragAdapter
    private lateinit var mViewPager:ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)

        initView()
        initData()
    }

    private fun initView(){
        mViewPager = findViewById(R.id.preview_viewpager)
    }

    private fun initData(){
        intent?.let {
            val bundle = intent.getBundleExtra("bundle")
            val previewDataList = bundle?.getParcelableArrayList<MediaPrev>("previewDataList")
            val position = bundle?.getInt("position")
            mAdapter = FragAdapter(supportFragmentManager, previewDataList)
            mViewPager.adapter = mAdapter
            if (position != null) {
                mViewPager.currentItem = position
            }
        }
    }
}