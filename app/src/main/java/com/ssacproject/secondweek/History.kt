package com.ssacproject.secondweek

import android.icu.number.IntegerWidth

class History {
    private var posterPath: String = ""
    private var title: String = ""
    private var progress: Int = 0
    private var duration: Int = 400

    constructor(posterPath: String, title: String, progress: Int, duration: Int) {
        this.posterPath = posterPath
        this.title = title
        this.progress = progress
        this.duration = duration
    }

    public fun getText(): String {
        return title
    }

    public fun getPosterPath(): String {
        return posterPath
    }

    public fun setText(title: String) {
        this.title = title
    }

    public fun setPosterPath(posterPath: String) {
        this.posterPath = posterPath
    }

    fun getProgress(): Int {
        return progress
    }

    fun setProgress(progress: Int) {
        this.progress = progress
    }

    fun getDuration(): Int {
        return duration
    }

    fun setDuration(duration: Int) {
        this.duration = duration
    }
}