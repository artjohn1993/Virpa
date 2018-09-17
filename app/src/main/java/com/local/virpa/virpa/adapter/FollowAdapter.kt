package com.local.virpa.virpa.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.local.virpa.virpa.R
import com.local.virpa.virpa.enum.Follow
import com.local.virpa.virpa.model.GetFollow
import com.squareup.picasso.Picasso

class FollowAdapter(var follower : GetFollow.ResultFollower?, var followed : GetFollow.ResultFollowed?) : RecyclerView.Adapter<FollowAdapter.FollowViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val layout = inflater.inflate(R.layout.layout_follow, parent, false)

        return FollowAdapter.FollowViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return getTotal(follower, followed)
    }
    override fun getItemViewType(position: Int): Int {
        return position
    }
    override fun onBindViewHolder(holder: FollowViewHolder, position: Int) {
        var pos = getItemViewType(position)
        if (identifyData(follower) == Follow.FOLLOWERS) {
            holder.name.text = follower?.data?.followers!![pos].user.detail.fullname
            var link = follower?.data?.followers!![pos].user.profilePicture?.filePath
            if (link != null) {
                Picasso.get().load(link).into(holder.profile)
            }
        }
        else {
            holder.name.text = followed?.data?.followed!![pos].user.detail.fullname
            var link = followed?.data?.followed!![pos].user.profilePicture?.filePath
            if (link != null) {
                Picasso.get().load(link).into(holder.profile)
            }
        }
    }

    class FollowViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var profile : ImageView = itemView.findViewById(R.id.profilePicture)
        var name : TextView = itemView.findViewById(R.id.name)
    }

    fun getTotal(data1 : GetFollow.ResultFollower?, data2 : GetFollow.ResultFollowed?) : Int {
        var total = 0
        if (data1 != null) {
            total = data1.data.followers.size
        }
        else {
            if (data2 != null) {
                total = data2.data.followed.size
            }
        }
        return total
    }
    fun identifyData(data1 : GetFollow.ResultFollower?) : Follow {
        if(data1 != null) {
            return Follow.FOLLOWERS
        }
        else {
            return Follow.FOLLOWED
        }
    }
}