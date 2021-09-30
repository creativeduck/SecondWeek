package com.ssacproject.secondweek

class MainMovie {
    var poster: Int = 0
    var textFirst: String?
    lateinit var textSecond: String
    lateinit var textThird: String
    lateinit var title: String

    constructor(poster: Int, textFirst: String?, textSecond: String, textThird: String, title: String) {
        this.poster = poster
        this.textFirst = textFirst
        this.textSecond = textSecond
        this.textThird = textThird
        this.title = title
    }
}
