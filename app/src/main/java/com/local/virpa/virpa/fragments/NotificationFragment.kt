package com.local.virpa.virpa.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import com.local.virpa.virpa.R
import com.local.virpa.virpa.adapter.FeedAdapter
import com.local.virpa.virpa.adapter.NotificationAdapter
import com.local.virpa.virpa.model.FirebaseModel


@SuppressLint("ValidFragment")
class NotificationFragment @SuppressLint("ValidFragment") constructor
(val activity : Activity,val array : ArrayList<FirebaseModel.Response>) : Fragment() {
    var notification : android.support.v7.widget.RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view  = inflater.inflate(R.layout.fragment_notification, container, false)
        notification = view.findViewById(R.id.notifRecycler)
        notification?.layoutManager = LinearLayoutManager(context,
                LinearLayout.VERTICAL,
                false)
        notification?.adapter = NotificationAdapter(activity, array)

        return view
    }

}
