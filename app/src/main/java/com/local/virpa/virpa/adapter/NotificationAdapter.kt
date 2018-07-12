package com.local.virpa.virpa.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.local.virpa.virpa.R

class NotificationAdapter : RecyclerView.Adapter<NotificationAdapter.NotifViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotifViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val layout = inflater.inflate(R.layout.layout_notification, parent, false)
        return NotificationAdapter.NotifViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: NotifViewHolder, position: Int) {

    }

    class NotifViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var icon : ImageView = itemView.findViewById(R.id.notifIcon)
        var title : TextView = itemView.findViewById(R.id.notifUserName)
        var desc : TextView = itemView.findViewById(R.id.notifDesc)
        var time : TextView = itemView.findViewById(R.id.notifTime)
    }
}