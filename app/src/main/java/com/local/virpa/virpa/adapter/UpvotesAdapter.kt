package com.local.virpa.virpa.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.local.virpa.virpa.R

class UpvotesAdapter : RecyclerView.Adapter<UpvotesAdapter.UpvotesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpvotesViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val layout = inflater.inflate(R.layout.layout_upvotes, parent, false)
        return UpvotesViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return 11
    }

    override fun onBindViewHolder(holder: UpvotesViewHolder, position: Int) {

    }

    class UpvotesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }
}