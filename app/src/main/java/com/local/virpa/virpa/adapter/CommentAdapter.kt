package com.local.virpa.virpa.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.local.virpa.virpa.R
import com.local.virpa.virpa.activity.VisitedProfileActivity
import com.local.virpa.virpa.model.GetBidder
import org.jetbrains.anko.startActivity

class CommentAdapter(var activity : Activity, var data : GetBidder.Result?) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val layout = inflater.inflate(R.layout.layout_comment, parent, false)
        return CommentViewHolder(layout)
    }


    override fun getItemCount(): Int {
        var total = 0
        if(data?.data?.bidders != null) {
            total = data?.data?.bidders?.size!!
        }

        return total
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        var pos = getItemViewType(position)
        var bidder = data?.data?.bidders!![pos]
        holder.name.text = bidder.user.detail.fullname

        when(bidder.status) {
            0 -> {
                holder.status.setBackgroundResource(R.drawable.color_primary_background)
                holder.status.text = "pending"
            }
            1 -> {
                holder.status.setBackgroundResource(R.drawable.color_orange_background)
                holder.status.text = "negotiating"
            }
            2 -> {
                holder.status.setBackgroundResource(R.drawable.color_green_background)
                holder.status.text = "accepted"
            }
        }
    }


    class CommentViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var picture = itemView.findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.bidderPicture)
        var name = itemView.findViewById<TextView>(R.id.bidderName)
        var status = itemView.findViewById<TextView>(R.id.bidderStatus)
        var time = itemView.findViewById<TextView>(R.id.bidderTime)
    }
}