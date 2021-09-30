package com.ssacproject.secondweek

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class CustomRecyclerAdapter() : RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder>() {
    var itemList: ArrayList<Item> = ArrayList<Item>()
    lateinit var mInflate: LayoutInflater
    lateinit var mContext: Context

    constructor(context: Context) : this() {
        mContext = context
        mInflate = LayoutInflater.from(context)
    }

    interface ItemClickListener {
        fun onItemClick(view: View, pos: Int)
    }
    var listener: HorizontalAdapter.ItemClickListener? = null

    fun setOnItemClickListener(listener: HorizontalAdapter.ItemClickListener) {
        this.listener = listener
    }

    fun setItem(item: Item) {
        itemList.add(item)
        notifyDataSetChanged()
    }

    fun setList(itemList: ArrayList<Item>) {
        this.itemList = itemList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        var holder: CustomRecyclerAdapter.ViewHolder = ViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var data: Item = itemList.get(position)
        holder.title?.setText(data.title)
        var imgId: String? = data.poster
        val url = "http://image.tmdb.org/t/p/w200${imgId}"
        Glide.with(mContext)
            .load(url)
            .centerCrop()
            .into(holder.poster!!)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder : RecyclerView.ViewHolder {
        var poster: ImageView? = null
        var title: TextView? = null

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