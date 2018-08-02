package com.local.virpa.virpa.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.local.virpa.virpa.R
import com.local.virpa.virpa.activity.CommentActivity
import com.local.virpa.virpa.activity.VisitedProfileActivity
import kotlinx.android.synthetic.main.fragment_feed.view.*
import org.jetbrains.anko.startActivity

class FeedAdapter(val activity: Activity) : RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val layout = inflater.inflate(R.layout.layout_post_feed, parent, false)
        return FeedViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
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
        var post : ImageView = itemView.findViewById(R.id.postImage)
        var commentText : TextView = itemView.findViewById(R.id.commentText)
        var commentIcon : ImageView = itemView.findViewById(R.id.commentIcon)
    }
}