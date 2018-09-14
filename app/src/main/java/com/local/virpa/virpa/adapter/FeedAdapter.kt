package com.local.virpa.virpa.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.local.virpa.virpa.R
import com.local.virpa.virpa.activity.CommentActivity
import com.local.virpa.virpa.activity.VisitedProfileActivity
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
        return feed.data.feeds.size
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
        holder.caption.text = feed.data.feeds[pos].budget.toString() + "\n \n" + feed.data.feeds[pos].body
        holder.upvotesTotal.text = feed.data.feeds[pos].upVoteCounts.toString() + " upvotes"
        holder.biddingTotal.text = feed.data.feeds[pos].biddingCounts.toString() + " bidding"
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



        holder.profile.setOnClickListener {
            activity.startActivity<VisitedProfileActivity>()
        }
        holder.name.setOnClickListener {
            activity.startActivity<VisitedProfileActivity>()
        }
        holder.biddingTotal.setOnClickListener {
            activity.startActivity<CommentActivity>()
        }
        holder.commentIcon.setOnClickListener {
            activity.startActivity<CommentActivity>()
        }
    }


    class FeedViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var profile : ImageView = itemView.findViewById(R.id.profilePicture)
        var name : TextView = itemView.findViewById(R.id.userName)
        var time : TextView = itemView.findViewById(R.id.time)
        var caption : TextView = itemView.findViewById(R.id.caption)
        var post : ImageView = itemView.findViewById(R.id.postImage)
        var upvotesTotal : TextView = itemView.findViewById(R.id.upVotesTotal)
        var biddingTotal : TextView = itemView.findViewById(R.id.biddingTotal)
        var commentIcon : ImageView = itemView.findViewById(R.id.commentIcon)
    }
}