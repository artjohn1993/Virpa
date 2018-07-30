package com.local.virpa.virpa.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.local.virpa.virpa.R

class RatingsAdapter : RecyclerView.Adapter<RatingsAdapter.RatingViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatingViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val layout = inflater.inflate(R.layout.layout_rating, parent, false)
        return RatingViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(holder: RatingViewHolder, position: Int) {

    }

    class RatingViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    }
}