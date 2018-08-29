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
import com.local.virpa.virpa.activity.*
import com.local.virpa.virpa.event.SignOutEvent
import com.local.virpa.virpa.model.SignOut
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.startActivity
import java.util.zip.Inflater

class SettingsAdapter(val activity: Activity) : RecyclerView.Adapter<SettingsAdapter.SettingsViewHolder>() {
    var title : ArrayList<String> = ArrayList()
    var guide : ArrayList<String> = ArrayList()
    var icon : ArrayList<Int> = ArrayList()
    var color: ArrayList<Int> = ArrayList()
    init {
        title.add("Total Views")
        title.add("Ratings")
        title.add("Location")
        title.add("Edit Profile")
        title.add("Skills")
        title.add("Logout")

        guide.add("See who viewed you")
        guide.add("View ratings")
        guide.add("Set your location")
        guide.add("Edit info")
        guide.add("Update your skills")
        guide.add("")

        icon.add(R.drawable.ic_view)
        icon.add(R.drawable.ic_rate_review)
        icon.add(R.drawable.ic_edit_location)
        icon.add(R.drawable.ic_edit)
        icon.add(R.drawable.ic_star_border)
        icon.add(R.drawable.ic_logout)

        color.add(R.drawable.circle_green)
        color.add(R.drawable.circle_red)
        color.add(R.drawable.circle_yellow)
        color.add(R.drawable.cirle_blue)
        color.add(R.drawable.circle_green)
        color.add(R.drawable.circle_red)

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
        holder.guide.text = guide[position]
        if (title[position] == "Logout") {
            holder.guide.visibility = View.GONE
            holder.next.visibility = View.GONE
        }
        holder.itemView.setOnClickListener {
            when(position) {
                0 -> {
                    activity.startActivity<ListViewActivity>()
                }
                1 -> {
                    activity.startActivity<RatingActivity>()
                }
                2 -> {
                    activity.startActivity<SetLocationActivity>()
                }
                3 -> {
                    activity.startActivity<EditInfoActivity>()
                }
                4 -> {
                    activity.startActivity<SkillsActivity>()
                }
                5 -> {
                    EventBus.getDefault().post(SignOutEvent())
                }

            }
        }
    }

    class SettingsViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val icon = itemview.findViewById<ImageView>(R.id.icon)
        val title = itemview.findViewById<TextView>(R.id.itemTitle)
        val guide = itemview.findViewById<TextView>(R.id.itemGuide)
        val next = itemview.findViewById<ImageView>(R.id.nextIcon)
    }
}