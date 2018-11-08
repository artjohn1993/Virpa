package com.local.virpa.virpa.adapter

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.local.virpa.virpa.R
import com.local.virpa.virpa.activity.ThreadActivity
import com.local.virpa.virpa.enum.NotifAction
import com.local.virpa.virpa.model.FirebaseModel

class NotificationAdapter(val activity : Activity,val array : MutableList<FirebaseModel.Response>) : RecyclerView.Adapter<NotificationAdapter.NotifViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotifViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val layout = inflater.inflate(R.layout.layout_notification, parent, false)
        return NotificationAdapter.NotifViewHolder(layout)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
    override fun getItemCount(): Int {
        var total = 0
        if (array != null) {
            total = array.size
        }
        return total
    }

    override fun onBindViewHolder(holder: NotifViewHolder, position: Int) {
        var pos = getItemViewType(position)
        holder.title.text = array[pos].name
        holder.desc.text = array[pos].description
        holder.time.text = ""

        holder.itemView.setOnClickListener {
            var intent = Intent(activity, ThreadActivity::class.java)
            intent.putExtra("bidderID" , array[pos].intent.bidderID)
            intent.putExtra("feedID" , array[pos].intent.feedID)
            intent.putExtra("feederID" , array[pos].intent.feederID)
            intent.putExtra("threadID", array[pos].intent.threadID)
            activity.startActivity(intent)
        }
        when(array[pos].action) {
            NotifAction.BID.getValue() -> {
                holder.icon.setImageResource(R.drawable.ic_bid)
            }
            NotifAction.MESSAGE.getValue() -> {
                holder.icon.setImageResource(R.drawable.ic_comment_black_24dp)
            }
            "" -> {
                holder.icon.setImageResource(R.drawable.ic_comment_black_24dp)
            }
        }
    }

    class NotifViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var icon : ImageView = itemView.findViewById(R.id.notifIcon)
        var title : TextView = itemView.findViewById(R.id.notifUserName)
        var desc : TextView = itemView.findViewById(R.id.notifDesc)
        var time : TextView = itemView.findViewById(R.id.notifTime)
    }
}