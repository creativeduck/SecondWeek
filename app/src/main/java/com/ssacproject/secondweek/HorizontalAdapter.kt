package com.ssacproject.secondweek

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HorizontalAdapter : RecyclerView.Adapter<HorizontalAdapter.HorizontalViewHolder>() {
    private lateinit var HorizontalDatas: ArrayList<Movies>

    interface ItemClickListener {
        fun onItemClick(view: View, pos: Int)
    }

    var listener: ItemClickListener? = null

    fun setOnItemClickListener(listener: ItemClickListener) {
        this.listener = listener
    }

    fun setData(list: ArrayList<Movies>) {
        HorizontalDatas = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalViewHolder {
        var view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        var holder: HorizontalViewHolder = HorizontalViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: HorizontalViewHolder, position: Int) {
        var data: Movies = HorizontalDatas.get(position)
        holder.title?.setText(data.getText())
        holder.poster?.setImageResource(data.getImg())
    }

    override fun getItemCount(): Int {
        return HorizontalDatas.size
    }

    inner class HorizontalViewHolder : RecyclerView.ViewHolder {
        public var poster: ImageView? = null
        public var title: TextView? = null

        constructor(itemView: View) : super(itemView) {
            poster = itemView.findViewById(R.id.poster)
            title = itemView.findViewById(R.id.title)

           itemView.setOnClickListener(View.OnClickListener {
               var pos: Int = adapterPosition
               if(pos != RecyclerView.NO_POSITION) {
                   if(listener != null) {
                       listener?.onItemClick(itemView, pos)
                       notifyItemChanged(pos)
                   }
               }
           })
        }

    }
}