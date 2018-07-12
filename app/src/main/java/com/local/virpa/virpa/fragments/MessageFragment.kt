package com.local.virpa.virpa.fragments

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
import com.local.virpa.virpa.adapter.MessageAdapter
import com.local.virpa.virpa.adapter.NotificationAdapter

class MessageFragment : Fragment() {
    var message : android.support.v7.widget.RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_message, container, false)
        message = view.findViewById(R.id.messageRecycler)
        message?.layoutManager = LinearLayoutManager(context,
                LinearLayout.VERTICAL,
                false)
        message?.adapter = MessageAdapter()
        return view
    }

}
