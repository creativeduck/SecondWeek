package com.ssacproject.secondweek

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

class CustomMainMovieView : FrameLayout {
    lateinit var custom_frame: FrameLayout
    lateinit var custom_relative: RelativeLayout
    lateinit var custom_poster: ImageView
    lateinit var textFirst: TextView
    lateinit var textSecond: TextView
    lateinit var textThird: TextView

    constructor(context: Context) : super(context) {
        init(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs, 0)
        getAttrs(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, delStyleAttr: Int) : super(context, attrs, delStyleAttr) {
        init(context, attrs, delStyleAttr)
        getAttrs(attrs, delStyleAttr)
    }

    private fun init(context: Context, attrs: AttributeSet?, delStyleAttr: Int) {
        var inflater: LayoutInflater? = null
        var tmp = getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)
        if (tmp is LayoutInflater) {
            inflater = tmp
        }
        var view : View = inflater!!.inflate(R.layout.mainmovie_main, this, false)
        addView(view)
        custom_frame = findViewById(R.id.custom_frame)
        custom_relative = findViewById(R.id.custom_relative)
        custom_poster = findViewById(R.id.custom_poster)
        textFirst = findViewById(R.id.textFirst)
        textSecond = findViewById(R.id.textSecond)
        textThird = findViewById(R.id.textThird)
    }

    private fun getAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomMainMovieView)
        setAttrs(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet?, delStyleAttr: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomMainMovieView)
        setAttrs(typedArray)
    }

    private fun setAttrs(typedArray: TypedArray) {
        custom_poster.setImageResource(typedArray.getResourceId(R.styleable.CustomMainMovieView_poster, R.drawable.poster_ready))
        textFirst.setText(typedArray.getText(R.styleable.CustomMainMovieView_textFirst))
        textSecond.setText(typedArray.getText(R.styleable.CustomMainMovieView_textSecond))
        textThird.setText(typedArray.getText(R.styleable.CustomMainMovieView_textThird))
    }


}