package com.local.virpa.virpa.adapter

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.local.virpa.virpa.R
import com.local.virpa.virpa.activity.ThreadActivity
import com.local.virpa.virpa.model.GetBidder

class BiddingAdapter(var activity : Activity, var data : GetBidder.Result?, var feederID : String, var currentUserID : String, var feedID : String) : RecyclerView.Adapter<BiddingAdapter.CommentViewHolder>() {

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

        if (!isReplyButtonEnable(bidder.user.detail.id)) {
            holder.comment.visibility = View.GONE
        }
        Glide.with(activity)
                .load(data?.data?.bidders!![pos].user.profilePicture.filePath)
                .into(holder.picture)

        when(bidder.status) {
            0 -> {
                holder.status.setBackgroundResource(R.drawable.color_primary_background)
                holder.status.text = "open"
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

        holder.comment.setOnClickListener {
            var intent = Intent(activity, ThreadActivity::class.java)
            intent.putExtra("bidderID" , data?.data?.bidders!![pos].user.detail.id)
            intent.putExtra("feedID" , feedID)
            intent.putExtra("feederID" , feederID)
            activity.startActivity(intent)
        }
    }


    class CommentViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var picture = itemView.findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.bidderPicture)
        var name = itemView.findViewById<TextView>(R.id.bidderName)
        var status = itemView.findViewById<TextView>(R.id.bidderStatus)
        var time = itemView.findViewById<TextView>(R.id.bidderTime)
        var comment = itemView.findViewById<LinearLayout>(R.id.threadGroup)
    }

    private fun isReplyButtonEnable(bidderID : String) : Boolean {
        var checker = false

        if (feederID == currentUserID) {
            checker = true
        }
        if (bidderID == currentUserID) {
            checker = true
        }
        return checker
    }
}