package com.local.virpa.virpa.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.local.virpa.virpa.R

class MessageAdapter : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val layout = inflater.inflate(R.layout.message_layout, parent, false)
        return MessageAdapter.MessageViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {

    }

    class MessageViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var icon : ImageView = itemView.findViewById(R.id.mProf)
        var name : TextView = itemView.findViewById(R.id.mName)
        var message : TextView = itemView.findViewById(R.id.mDesc)
        var time : TextView = itemView.findViewById(R.id.mTime)
    }
}