package com.jesen.code.ffmpegplaykot.widget

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.Surface
import android.view.SurfaceView
import androidx.annotation.RequiresApi
import com.jesen.code.ffmpegplaykot.util.FUtils.playVideo

class FFVideoView : SurfaceView {
    private var mSurface: Surface? = null

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
        init()
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init()
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    private fun init() {
        holder.setFormat(PixelFormat.RGBA_8888)
        mSurface = holder.surface
    }

    fun playVideo(videoPath: String?) {
        Thread(Runnable {
            Log.d("FFVideoView", "run: playVideo")
            playVideo(videoPath, mSurface)
        }).start()
    }
}