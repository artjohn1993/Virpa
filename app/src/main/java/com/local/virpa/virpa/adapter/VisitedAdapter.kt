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
import org.jetbrains.anko.startActivity

class VisitedAdapter(val activity: Activity) : RecyclerView.Adapter<VisitedAdapter.VisitediewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisitediewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val layout = inflater.inflate(R.layout.layout_post_feed, parent, false)
        return VisitediewHolder(layout)
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun onBindViewHolder(holder: VisitediewHolder, position: Int) {
        holder.commentText.setOnClickListener {
            activity.startActivity<CommentActivity>()
        }
        holder.commentIcon.setOnClickListener {
            activity.startActivity<CommentActivity>()
        }
    }


    class VisitediewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var profile : ImageView = itemView.findViewById(R.id.profilePicture)
        var name : TextView = itemView.findViewById(R.id.userName)
        var time : TextView = itemView.findViewById(R.id.time)
        var post : ImageView = itemView.findViewById(R.id.postImage)
        var commentText : TextView = itemView.findViewById(R.id.commentText)
        var commentIcon : ImageView = itemView.findViewById(R.id.commentIcon)
    }
}