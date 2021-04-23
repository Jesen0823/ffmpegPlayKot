package com.jesen.code.ffmpegplaykot.filter

interface IFilter<T> {
    fun doFilter(t:T)
}