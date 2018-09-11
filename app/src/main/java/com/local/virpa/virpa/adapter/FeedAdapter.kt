package com.local.virpa.virpa.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.local.virpa.virpa.R
import com.local.virpa.virpa.activity.CommentActivity
import com.local.virpa.virpa.activity.VisitedProfileActivity
import com.local.virpa.virpa.model.Feed
import org.jetbrains.anko.startActivity

class FeedAdapter(val activity: Activity,val feed : Feed.Result) : RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val layout = inflater.inflate(R.layout.layout_post_feed, parent, false)
        return FeedViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return feed.data.feeds.size
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.caption.text = feed.data.feeds[position].body

        try {
            var sample = feed.data.feeds[position].coverPhotos[0].extension
            if(sample != "") {
                Glide.with(activity)
                        .load(feed.data.feeds[position].coverPhotos[0].filePath)
                        .into(holder.post)
            }
            else {
                holder.post.visibility = View.GONE
            }

        }catch (e : Exception) {
            holder.post.visibility = View.GONE
        }


        holder.profile.setOnClickListener {
            activity.startActivity<VisitedProfileActivity>()
        }
        holder.name.setOnClickListener {
            activity.startActivity<VisitedProfileActivity>()
        }
        holder.commentText.setOnClickListener {
            activity.startActivity<CommentActivity>()
        }
        holder.commentIcon.setOnClickListener {
            activity.startActivity<CommentActivity>()
        }
    }


    class FeedViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var profile : ImageView = itemView.findViewById(R.id.profilePicture)
        var name : TextView = itemView.findViewById(R.id.userName)
        var time : TextView = itemView.findViewById(R.id.time)
        var caption : TextView = itemView.findViewById(R.id.caption)
        var post : ImageView = itemView.findViewById(R.id.postImage)
        var commentText : TextView = itemView.findViewById(R.id.commentText)
        var commentIcon : ImageView = itemView.findViewById(R.id.commentIcon)
    }
}