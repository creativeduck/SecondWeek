package com.ssacproject.secondweek

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomPagerAdapter : RecyclerView.Adapter<Holder>() {
    var mainMovieList = listOf<MainMovie>()
    var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClicked(view: View, pos: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(parent)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setMovie(mainMovieList[position])
    }

    override fun getItemCount(): Int {
        return mainMovieList.size
    }


}


class Holder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    constructor(parent: ViewGroup) : this(
        LayoutInflater.from(parent.context).inflate(
            R.layout.mainmovie_main,
            parent, false
        )
    )

    fun setMovie(movie: MainMovie) {
        var custom_poster: ImageView = itemView.findViewById(R.id.custom_poster)
        var textFirst: TextView = itemView.findViewById(R.id.textFirst)
        var textSecond: TextView = itemView.findViewById(R.id.textSecond)
        var textThird: TextView = itemView.findViewById(R.id.textThird)

        custom_poster.setImageResource(movie.poster)
        textFirst.setText(movie.textFirst)
        textSecond.setText(movie.textSecond)
        textThird.setText(movie.textThird)
    }
}