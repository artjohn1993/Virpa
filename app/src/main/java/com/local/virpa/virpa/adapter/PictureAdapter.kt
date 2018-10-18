package com.local.virpa.virpa.adapter


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import com.local.virpa.virpa.R


class PictureAdapter : RecyclerView.Adapter<PictureAdapter.PictureViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): PictureViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val layout = inflater.inflate(R.layout.layout_image, parent, false)
        return PictureAdapter.PictureViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(p0: PictureViewHolder, p1: Int) {

    }


    class PictureViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    }
}