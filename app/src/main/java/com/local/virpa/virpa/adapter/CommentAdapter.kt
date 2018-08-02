package com.local.virpa.virpa.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.local.virpa.virpa.R
import com.local.virpa.virpa.activity.VisitedProfileActivity
import org.jetbrains.anko.startActivity

class CommentAdapter : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val layout = inflater.inflate(R.layout.layout_comment, parent, false)
        return CommentViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return 12
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {

    }


    class CommentViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    }
}