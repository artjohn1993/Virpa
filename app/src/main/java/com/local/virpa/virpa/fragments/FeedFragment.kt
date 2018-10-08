package com.local.virpa.virpa.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import com.local.virpa.virpa.R
import com.local.virpa.virpa.activity.PostActivity
import com.local.virpa.virpa.adapter.FeedAdapter
import com.local.virpa.virpa.enum.FragmentType
import com.local.virpa.virpa.event.Refresh
import com.local.virpa.virpa.model.Feed
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.startActivity

@SuppressLint("ValidFragment")
class FeedFragment @SuppressLint("ValidFragment") constructor
(val activity: Activity,val data : Feed.Result?,val initial : Boolean) : Fragment() {

    var feedRecycler : android.support.v7.widget.RecyclerView? = null
    var fab : android.support.design.widget.FloatingActionButton? = null
    var empty :android.support.constraint.ConstraintLayout? = null
    var refresh : android.support.v4.widget.SwipeRefreshLayout? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_feed, container, false)

        feedRecycler = view.findViewById(R.id.feedRecyclerView)
        fab = view.findViewById(R.id.fabButton)
        empty = view.findViewById(R.id.emptyFeedCon)
        refresh = view.findViewById(R.id.feedRefresh)

        refresh?.isRefreshing = initial

        refresh?.setOnRefreshListener {
            EventBus.getDefault().post(Refresh(FragmentType.FEED))
        }
        feedRecycler?.layoutManager = LinearLayoutManager(context,
                LinearLayout.VERTICAL,
                false)
        if(data?.data != null) {
            feedRecycler?.adapter = FeedAdapter(activity, data)
            empty?.visibility = View.GONE
        }
        else {
            empty?.visibility = View.VISIBLE
        }
        fab?.setOnClickListener {
            activity.startActivity<PostActivity>()
            activity.finish()
        }

        return view
    }
}
