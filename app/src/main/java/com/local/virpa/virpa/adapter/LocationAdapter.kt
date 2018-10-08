package com.local.virpa.virpa.adapter

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.local.virpa.virpa.R
import com.local.virpa.virpa.activity.VisitedProfileActivity
import com.local.virpa.virpa.model.UserList
import com.squareup.moshi.Moshi
import com.squareup.picasso.Picasso

class LocationAdapter(val activity: Activity,var data: UserList.Result) : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {
    var dataArray = data.data.users

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val layout = inflater.inflate(R.layout.layout_location, parent, false)
        return LocationViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return dataArray.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        var pos = getItemViewType(position)

        if (dataArray[pos].profilePicture != null ) {
            Picasso.get().load(dataArray[pos].profilePicture[0]?.filePath).into(holder.image)
        }

        holder.name.text = dataArray[pos].fullname
        holder.itemView.setOnClickListener {
            goToProfile(dataArray[pos])
        }

    }

    class LocationViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.img)
        val name = itemView.findViewById<TextView>(R.id.name)
        val distance = itemView.findViewById<TextView>(R.id.distance)
    }

    private fun goToProfile(data: UserList.Users) {
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter<UserList.Users>(UserList.Users::class.java)
        var jsonData = adapter.toJson(data)
        var intent = Intent(activity, VisitedProfileActivity::class.java)
        intent.putExtra("userInfo", jsonData)
        activity.startActivity(intent)
    }
}