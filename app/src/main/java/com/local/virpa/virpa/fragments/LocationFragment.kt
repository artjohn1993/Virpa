package com.local.virpa.virpa.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.local.virpa.virpa.R

import android.widget.ImageButton
import com.local.virpa.virpa.activity.HomeActivity


@SuppressLint("ValidFragment")
class LocationFragment @SuppressLint("ValidFragment") constructor
(val activity: HomeActivity) : Fragment() {


    var fragment : FragmentTransaction? = null
    var changeView : ImageButton? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_location, container, false)

        assignView(view)

        changeView?.setOnClickListener {
            setLayout(LocationView2Fragment(), "2")
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.fragment = childFragmentManager.beginTransaction()
        setLayout(LocationView1Fragment(), "1")
    }

    private fun assignView(view : View) {
        changeView = view.findViewById(R.id.changeView)
    }

    private fun setLayout(data : android.support.v4.app.Fragment, type : String) {
        this.fragment?.replace(R.id.locationFrame, data, type)
        this.fragment?.commit()
    }
}
