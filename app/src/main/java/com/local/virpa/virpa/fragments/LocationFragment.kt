package com.local.virpa.virpa.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.local.virpa.virpa.R

import android.widget.ImageButton
import android.widget.LinearLayout
import com.local.virpa.virpa.activity.HomeActivity
import com.local.virpa.virpa.adapter.FeedAdapter
import com.local.virpa.virpa.adapter.LocationAdapter
import com.local.virpa.virpa.model.UserList


@SuppressLint("ValidFragment")
class LocationFragment @SuppressLint("ValidFragment") constructor
(val activity: HomeActivity,val data: UserList.Result?) : Fragment() {


    var fragment : RecyclerView? = null
    var locationRecycler : android.support.v7.widget.RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_location, container, false)
        locationRecycler = view.findViewById(R.id.locationRecycler)
        locationRecycler?.layoutManager = GridLayoutManager(context, 3)
        if(data != null) {
            locationRecycler?.adapter = LocationAdapter(activity, this.data!!)
        }

        return view
    }

}
