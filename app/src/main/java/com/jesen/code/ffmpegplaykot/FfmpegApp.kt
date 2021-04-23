package com.jesen.code.ffmpegplaykot

import android.app.Application
import android.content.ContextWrapper

private lateinit var INSTANCE:Application

class FfmpegApp:Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}

object AppContext: ContextWrapper(INSTANCE)