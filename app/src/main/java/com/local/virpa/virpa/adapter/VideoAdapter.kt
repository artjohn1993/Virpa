package com.local.virpa.virpa.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.local.virpa.virpa.R

class VideoAdapter : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): VideoViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val layout = inflater.inflate(R.layout.layout_video, parent, false)
        return VideoAdapter.VideoViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(p0: VideoViewHolder, p1: Int) {

    }


    class VideoViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    }
}