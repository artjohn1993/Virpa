package com.local.virpa.virpa.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import com.local.virpa.virpa.R
import com.local.virpa.virpa.adapter.PictureAdapter
import com.local.virpa.virpa.adapter.RatingsAdapter


@SuppressLint("ValidFragment")
class PicturesFragment(var activity : Activity) : Fragment() {

    var recycler : RecyclerView? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view =  inflater.inflate(R.layout.fragment_pictures, container, false)
        recycler = view.findViewById(R.id.pictureRecycler)
        setRecycler()
        return view
    }
    private fun setRecycler() {
        recycler!!.layoutManager = GridLayoutManager(activity,
                3,
                GridLayoutManager.VERTICAL,
                false)
        recycler!!.adapter = PictureAdapter()
        recycler!!.isFocusable = false
    }

}
