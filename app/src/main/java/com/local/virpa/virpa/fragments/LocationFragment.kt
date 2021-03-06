package com.local.virpa.virpa.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.local.virpa.virpa.R

import com.local.virpa.virpa.activity.HomeActivity
import com.local.virpa.virpa.adapter.LocationAdapter
import com.local.virpa.virpa.enum.FragmentType
import com.local.virpa.virpa.event.Refresh
import com.local.virpa.virpa.model.UserList
import org.greenrobot.eventbus.EventBus


@SuppressLint("ValidFragment")
class LocationFragment @SuppressLint("ValidFragment") constructor
(val activity: HomeActivity, val data: UserList.Result?, var initial : Boolean) : Fragment() {


    var fragment : RecyclerView? = null
    var locationRecycler : android.support.v7.widget.RecyclerView? = null
    var refresh : android.support.v4.widget.SwipeRefreshLayout? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_location, container, false)
        locationRecycler = view.findViewById(R.id.locationRecycler)
        locationRecycler?.layoutManager = GridLayoutManager(context, 3)
        refresh = view.findViewById(R.id.locationRefresh)

        refresh?.isRefreshing = initial

        refresh?.setOnRefreshListener {
            EventBus.getDefault().post(Refresh(FragmentType.LOCATION))
        }

        if(data != null) {
            locationRecycler?.adapter = LocationAdapter(activity, this.data!!)
        }

        return view
    }

}
