package com.ssacproject.secondweek

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class CustomRecommendView : ConstraintLayout {
    lateinit var constraint: ConstraintLayout
    lateinit var keyword: TextView
    lateinit var showmore: TextView
    lateinit var recycler: RecyclerView

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
        var view : View = inflater!!.inflate(R.layout.custom_recommend_main, this, false)
        addView(view)
        constraint = findViewById(R.id.constraint)
        keyword = findViewById(R.id.keyword)
        showmore = findViewById(R.id.showmore)
        recycler = findViewById(R.id.recycler)
    }

    private fun getAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomRecommendView)
        setAttrs(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet?, delStyleAttr: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomRecommendView)
        setAttrs(typedArray)
    }

    private fun setAttrs(typedArray: TypedArray) {
        keyword.setText(typedArray.getText(R.styleable.CustomRecommendView_keyword))
    }

}