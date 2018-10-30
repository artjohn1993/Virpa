package com.local.virpa.virpa.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.local.virpa.virpa.R
import com.local.virpa.virpa.activity.BiddingActivity
import com.local.virpa.virpa.activity.VisitedProfileActivity
import com.local.virpa.virpa.dialog.FullImageDialog
import com.local.virpa.virpa.event.GetTime
import com.local.virpa.virpa.model.Feed
import org.jetbrains.anko.startActivity

class FeedAdapter(val activity: Activity,val feed : Feed.Result) : RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val layout = inflater.inflate(R.layout.layout_post_feed, parent, false)
        return FeedViewHolder(layout)
    }

    override fun getItemCount(): Int {
        try {
            return feed.data.feeds.size
        }catch (e : NullPointerException) {
            println(e.toString())
            return 0
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        var pos = getItemViewType(position)
        var sample1 =  feed.data.feeds[pos].createdAt

        holder.time.text = GetTime.calculate(feed.data.feeds[pos].createdAt)
        holder.name.text = feed.data.feeds[pos].feeder

        if (feed.data.feeds[pos].profilePicture != null) {
            Glide.with(activity)
                    .load(feed.data.feeds[pos].profilePicture?.filePath)
                    .into(holder.profile)
        }

        if(feed.data.feeds[pos].coverPhotos != null) {
            Glide.with(activity)
                    .load(feed.data.feeds[pos].coverPhotos!![0].filePath)
                    .into(holder.post)
        }
        else {
            holder.post.visibility = View.GONE
        }
        if (feed.data.feeds[pos].type != 0) {
            holder.type.text = "Announcement"
            holder.type.setBackgroundResource(R.drawable.color_primary_background)
            holder.caption.text = feed.data.feeds[pos].body
            holder.biddingTotal.text = " Comment"
        }
        else {
            holder.caption.text = feed.data.feeds[pos].budget.toString() + "\n \n" + feed.data.feeds[pos].body
            holder.biddingTotal.text = feed.data.feeds[pos].biddingCounts.toString() + " Bidding"
        }


        holder.profile.setOnClickListener {
            gotoVisit(feed.data.feeds[pos].feeder,
                    feed.data.feeds[pos].feederId
            , feed.data.feeds[pos].profilePicture?.filePath!!)
        }
        holder.name.setOnClickListener {
            gotoVisit(feed.data.feeds[pos].feeder,
                    feed.data.feeds[pos].feederId,
                    feed.data.feeds[pos].profilePicture?.filePath!!)
        }
        holder.biddingTotal.setOnClickListener {
            gotoBidding(feed.data.feeds[pos].type,
                    feed.data.feeds[pos].feedId,
                    feed.data.feeds[pos].feederId

            )
        }
        holder.commentIcon.setOnClickListener {
            gotoBidding(feed.data.feeds[pos].type,
                    feed.data.feeds[pos].feedId,
                    feed.data.feeds[pos].feederId)
        }
        holder.post.setOnClickListener {
            FullImageDialog(activity).show(feed.data.feeds[pos].coverPhotos!![0].filePath)
        }
    }


    class FeedViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var profile : ImageView = itemView.findViewById(R.id.profilePicture)
        var name : TextView = itemView.findViewById(R.id.userName)
        var time : TextView = itemView.findViewById(R.id.time)
        var caption : TextView = itemView.findViewById(R.id.caption)
        var post : ImageView = itemView.findViewById(R.id.postImage)
        var biddingTotal : TextView = itemView.findViewById(R.id.biddingTotal)
        var commentIcon : ImageView = itemView.findViewById(R.id.commentIcon)
        var type : TextView = itemView.findViewById(R.id.postType)
        var biddingGroup : LinearLayout = itemView.findViewById(R.id.reactionGroup)
        var location : TextView = itemView.findViewById(R.id.userLocation)
    }

    fun gotoBidding(type : Int, feedID : String, feederID : String) {
        if(type == 0) {
            var intent = Intent(activity, BiddingActivity::class.java)
            intent.putExtra("feedID", feedID)
            intent.putExtra("feederID", feederID)
            activity.startActivity(intent)
        }
        else {
            
        }
    }
    fun gotoVisit(name : String, id : String, profile : String) {
        var intent = Intent(activity, VisitedProfileActivity::class.java)
        intent.putExtra("name", name)
        intent.putExtra("id", id)
        intent.putExtra("profile", profile)
        activity.startActivity(intent)
    }

}