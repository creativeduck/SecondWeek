package com.ssacproject.secondweek

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssacproject.secondweek.databinding.RecordMainBinding

class RecordAdapter : RecyclerView.Adapter<RecordAdapter.Holder>() {
    var helper: SQLiteHelper? = null
    var recordList = mutableListOf<Record>()
    var mContext: Context? = null

    interface ItemClickListener {
        fun onItemClick(view: View, pos: Int)
    }
    var listener: ItemClickListener? = null
    fun setOnItemClickListener(listener: ItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = RecordMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val record = recordList.get(position)
        holder.setRecord(record)
    }

    override fun getItemCount(): Int {
        return recordList.size
    }

    inner class Holder(val binding: RecordMainBinding) : RecyclerView.ViewHolder(binding.root) {
        var mRecord: Record? = null
        init {
            binding.btnDelete.setOnClickListener({
                helper?.deleteRecord(mRecord!!)
                recordList.remove(mRecord)
                val title = mRecord!!.title
                var pref: SharedPreferences = mContext!!.getSharedPreferences("prefPlay", Activity.MODE_PRIVATE)
                var editor: SharedPreferences.Editor = pref.edit()
                editor.putInt("position${title}", 0)
                editor.apply()
                notifyDataSetChanged()
            })

            binding.root.setOnClickListener( {
                var pos: Int = adapterPosition
                if(pos!= RecyclerView.NO_POSITION) {
                    if(listener != null) {
                        listener?.onItemClick(binding.root, pos)
                            notifyItemChanged(pos)
                    }
                }
            })
        }

        fun setRecord(record: Record) {
            binding.textTitle.text = record.title
            binding.recordPoster.setImageResource(R.drawable.poster_spiderman3)
            val url = "http://image.tmdb.org/t/p/w200${record.poster}"
            Glide.with(binding.recordPoster!!.context.applicationContext)
                .load(url)
                .fitCenter()
                .into(binding.recordPoster)
            binding.recordProgress.setProgress(record.progress.toInt())
            binding.recordProgress.max = record.duration.toInt()

            this.mRecord = record
        }
    }
}