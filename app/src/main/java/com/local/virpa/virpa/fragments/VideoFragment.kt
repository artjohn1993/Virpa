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
import com.local.virpa.virpa.adapter.PictureAdapter
import com.local.virpa.virpa.adapter.VideoAdapter


@SuppressLint("ValidFragment")
class VideoFragment @SuppressLint("ValidFragment") constructor
(var activity : Activity) : Fragment() {

    var recycler : RecyclerView? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view  = inflater.inflate(R.layout.fragment_video, container, false)
        recycler = view.findViewById(R.id.videoRecycler)
        setRecycler()
        return view
    }
    private fun setRecycler() {
        recycler!!.layoutManager = LinearLayoutManager(activity,
                LinearLayout.VERTICAL,
                false)
        recycler!!.adapter = VideoAdapter()
        recycler!!.isFocusable = false
    }
}
