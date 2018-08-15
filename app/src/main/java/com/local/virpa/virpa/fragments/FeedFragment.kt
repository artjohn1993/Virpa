package com.local.virpa.virpa.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import com.local.virpa.virpa.R
import com.local.virpa.virpa.adapter.FeedAdapter
import com.local.virpa.virpa.model.Feed

@SuppressLint("ValidFragment")
class FeedFragment @SuppressLint("ValidFragment") constructor
(val activity: Activity,val data : Feed.Result) : Fragment() {

    var feedRecycler : android.support.v7.widget.RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_feed, container, false)

        feedRecycler = view.findViewById(R.id.feedRecyclerView)
        feedRecycler?.layoutManager = LinearLayoutManager(context,
                LinearLayout.VERTICAL,
                false)
        feedRecycler?.adapter = FeedAdapter(activity)

        return view
    }
}
