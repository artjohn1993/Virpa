package com.local.virpa.virpa.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.local.virpa.virpa.R
import com.local.virpa.virpa.model.FeedThread

class FTAdapter(var data : List<FeedThread.Message>,var currentUser : String) : RecyclerView.Adapter<FTAdapter.FeedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val layout = inflater.inflate(R.layout.layout_feed_thread, parent, false)
        return FeedViewHolder(layout)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        var total = 0
        if (data != null) {
            total = data.size
        }

        return data.size
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        var pos = getItemViewType(position)

        if (data[pos].id == currentUser) {
            holder.message2.text = data[pos].message
            holder.con1.visibility = View.GONE
        }
        else {
            holder.message1.text = data[pos].message
            holder.con2.visibility = View.GONE
        }
    }

    class FeedViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var message1 = itemView.findViewById<TextView>(R.id.message1)
        var message2 = itemView.findViewById<TextView>(R.id.message2)

        var con1 = itemView.findViewById<LinearLayout>(R.id.message1Con)
        var con2 = itemView.findViewById<LinearLayout>(R.id.message2Con)
    }
}