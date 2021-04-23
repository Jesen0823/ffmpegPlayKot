package com.jesen.code.ffmpegplaykot.util.extend

import android.content.Context
import android.content.Intent

inline fun <reified T> simpleStartActivity(context: Context, block:Intent.() -> Unit){
    val intent = Intent(context, T::class.java)
    intent.block()
    context.startActivity(intent)
}