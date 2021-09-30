package com.ssacproject.secondweek

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssacproject.secondweek.databinding.HistoryMainBinding

class CustomHistoryAdapter : RecyclerView.Adapter<CustomHistoryAdapter.Holder>() {
    var historyList = ArrayList<History>()
//    var helper: SQLiteHelper? = null

    interface ItemClickListener {
        fun onItemClick(view: View, pos: Int)
    }
    var listener: ItemClickListener? = null
    fun setOnItemClickListener(listener: ItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
//        val binding = HistoryMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        var view: View = LayoutInflater.from(parent.context).inflate(R.layout.history_main, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
//        val history = historyList.get(position)
//        holder.setHistory(history)

        var data: History = historyList.get(position)
        holder.history_title?.setText(data.getText())

        val url = "http://image.tmdb.org/t/p/w200${data.getPosterPath()}"

        Glide.with(holder.history_poster!!.context.applicationContext)
            .load(url)
            .fitCenter()
            .into(holder.history_poster!!)

//        holder.history_poster?.setImageResource(data.getImg())
        holder.progress?.setProgress(data.getProgress())
        holder.progress?.max = data.getDuration()
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    inner class Holder : RecyclerView.ViewHolder {
        var history_poster: com.google.android.material.imageview.ShapeableImageView? = null
        var history_title: TextView? = null
        var progress: ProgressBar? = null

        constructor(itemView: View) : super(itemView) {
            history_poster = itemView.findViewById(R.id.history_poster)
            history_title = itemView.findViewById(R.id.history_title)
            progress = itemView.findViewById(R.id.progress)

            itemView.setOnClickListener({
                var pos: Int = adapterPosition
                if(pos != RecyclerView.NO_POSITION) {
                    if(listener != null) {
                        listener?.onItemClick(itemView, pos)
                        notifyItemChanged(pos)
                    }
                }
            })
        }

//        var mHistory: History? = null
//
//        fun setHistory(history: History) {
//            binding.historyTitle.setText(history.getText())
//            binding.progress.setProgress(history.getProgress().toInt())
//            binding.progress.max = history.getDuration().toInt()
//            val url = "http://image.tmdb.org/t/p/w200${history.getPosterPath()}"
//            Glide.with(binding.historyPoster.context.applicationContext)
//                .load(url)
//                .fitCenter()
//                .crossFade()
//                .into(binding.historyPoster)
//
//        }

    }

}