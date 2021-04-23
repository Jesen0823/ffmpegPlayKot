package com.jesen.code.ffmpegplaykot.util

import android.content.Context
import java.lang.Exception

object CommonUtil {

    fun getAppName(context: Context):String?{
        try {
            val packageManager = context.packageManager
            val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
            val label = packageInfo.applicationInfo.labelRes
            return context.resources.getString(label)
        }catch (e:Exception){
            e.printStackTrace()
        }
        return "PlayKot"
    }
}