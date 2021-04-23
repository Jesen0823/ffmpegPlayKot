package com.jesen.code.ffmpegplaykot.util

import android.view.Surface

object FUtils {
    external fun urlProtocolInfo(): String?
    external fun avFormatInfo(): String?
    external fun avCodecInfo(): String?
    external fun avFilterInfo(): String?
    @JvmStatic
    external fun playVideo(videoPath: String?, surface: Surface?)
}