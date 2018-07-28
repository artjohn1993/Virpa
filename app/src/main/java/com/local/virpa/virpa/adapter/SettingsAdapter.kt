package com.local.virpa.virpa.adapter

import android.app.Activity
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.local.virpa.virpa.R
import org.jetbrains.anko.backgroundDrawable
import java.util.zip.Inflater

class SettingsAdapter(val activity: Activity) : RecyclerView.Adapter<SettingsAdapter.SettingsViewHolder>() {
    var title : ArrayList<String> = ArrayList()
    var icon : ArrayList<Int> = ArrayList()
    var color: ArrayList<Int> = ArrayList()
    init {
        title.add("Total Views")
        title.add("Ratings")
        title.add("Upvotes")
        title.add("Edit Profile")
        title.add("Logout")

        icon.add(R.drawable.ic_view)
        icon.add(R.drawable.ic_rate_review)
        icon.add(R.drawable.ic_up_vote)
        icon.add(R.drawable.ic_edit)
        icon.add(R.drawable.ic_logout)

        color.add(R.drawable.circle_green)
        color.add(R.drawable.circle_red)
        color.add(R.drawable.circle_yellow)
        color.add(R.drawable.cirle_blue)
        color.add(R.drawable.circle_green)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.layout_profile,parent,false)
        return SettingsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return title.size
    }

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        holder.icon.setImageResource(icon[position])
        holder.icon.backgroundDrawable = ContextCompat.getDrawable(activity,color[position])
        holder.title.text = title[position]
        if (title[position] == "Logout") {
            holder.guide.visibility = View.GONE
            holder.next.visibility = View.GONE
        }
    }

    class SettingsViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val icon = itemview.findViewById<ImageView>(R.id.icon)
        val title = itemview.findViewById<TextView>(R.id.itemTitle)
        val guide = itemview.findViewById<TextView>(R.id.itemGuide)
        val next = itemview.findViewById<ImageView>(R.id.nextIcon)

    }
}