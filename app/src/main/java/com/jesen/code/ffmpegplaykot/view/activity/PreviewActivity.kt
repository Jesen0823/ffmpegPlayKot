package com.jesen.code.ffmpegplaykot.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.jesen.code.ffmpegplaykot.R
import com.jesen.code.ffmpegplaykot.adapter.FragAdapter
import com.jesen.code.ffmpegplaykot.databean.MediaPrev
import com.jesen.code.ffmpegplaykot.util.AppLog
private const val TAG = "PreviewActivity"
class PreviewActivity : AppCompatActivity() {
    private lateinit var mAdapter: FragAdapter
    private lateinit var mViewPager:ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)
        AppLog.i(TAG,"onCreate")
        initView()
        initData()
    }

    override fun onPause() {
        super.onPause()
        AppLog.i(TAG,"onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        AppLog.i(TAG,"onDestroy")
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