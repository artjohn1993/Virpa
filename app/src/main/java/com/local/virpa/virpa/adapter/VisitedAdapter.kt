package com.local.virpa.virpa.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.local.virpa.virpa.R
import com.local.virpa.virpa.activity.BiddingActivity
import com.local.virpa.virpa.dialog.FullImageDialog
import com.local.virpa.virpa.event.GetTime
import com.local.virpa.virpa.model.FeedByUser
import org.jetbrains.anko.startActivity

class VisitedAdapter(val activity: Activity, var data: FeedByUser.Result) : RecyclerView.Adapter<VisitedAdapter.VisitediewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisitediewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val layout = inflater.inflate(R.layout.layout_visited_user, parent, false)
        return VisitediewHolder(layout)
    }

    override fun getItemCount(): Int {
        return data.data.feeds.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: VisitediewHolder, position: Int) {
        var pos = getItemViewType(position)

        holder.time.text = GetTime.calculate(data.data.feeds[pos].createdAt)
        holder.price.text = data.data.feeds[pos].budget.toString()
        holder.caption.text = data.data.feeds[pos].body
        if (data.data.feeds[pos].type != 0)
        {
            holder.price.visibility = View.GONE
            holder.groupReaction.visibility = View.GONE
            holder.postType.text = "Announcement"
            holder.postType .setBackgroundResource(R.drawable.color_primary_background)
        }
        if (data.data.feeds[pos].coverPhotos != null) {
            Glide.with(activity)
                    .load(data.data.feeds[pos].coverPhotos!![0].filePath)
                    .into(holder.post)
        }
        else {
            holder.post.visibility = View.GONE
        }

        holder.bidding.setOnClickListener {
            activity.startActivity<BiddingActivity>()
        }
        holder.commentIcon.setOnClickListener {
            activity.startActivity<BiddingActivity>()
        }
        holder.post.setOnClickListener {
            FullImageDialog(activity).show(data.data.feeds[pos].coverPhotos!![0].filePath)
        }
    }


    class VisitediewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var time : TextView = itemView.findViewById(R.id.time)
        var price : TextView = itemView.findViewById(R.id.price)
        var caption : TextView = itemView.findViewById(R.id.caption)
        var post : ImageView = itemView.findViewById(R.id.postImage)
        var bidding : TextView = itemView.findViewById(R.id.biddingTotal)
        var commentIcon : ImageView = itemView.findViewById(R.id.commentIcon)
        var groupReaction : LinearLayout = itemView.findViewById(R.id.reactionGroup)
        var postType : TextView = itemView.findViewById(R.id.visitedPostType)
    }
}