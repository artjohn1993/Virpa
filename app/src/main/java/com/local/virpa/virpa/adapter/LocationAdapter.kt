package com.local.virpa.virpa.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.local.virpa.virpa.R
import com.local.virpa.virpa.activity.VisitedProfileActivity
import org.jetbrains.anko.startActivity

class LocationAdapter(val activity: Activity) : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val layout = inflater.inflate(R.layout.layout_location, parent, false)
        return LocationViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return 30
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            activity.startActivity<VisitedProfileActivity>()
        }

    }


    class LocationViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.img)
    }
}