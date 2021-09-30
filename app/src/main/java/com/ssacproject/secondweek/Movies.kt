package com.ssacproject.secondweek

class Movies {
    private var img: Int = 0
    private var title: String = ""

    constructor(img: Int, title: String) {
        this.img = img
        this.title = title
    }

    public fun getText(): String {
        return title
    }
    public fun getImg(): Int {
        return img
    }
    public fun setText(title: String) {
        this.title = title
    }
    public fun setImg(int: Int) {
        this.img = img
    }

}