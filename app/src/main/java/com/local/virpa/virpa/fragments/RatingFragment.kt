package com.local.virpa.virpa.fragments
import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import com.local.virpa.virpa.R
import com.local.virpa.virpa.adapter.RatingsAdapter
import kotlinx.android.synthetic.main.activity_rating.*

@SuppressLint("ValidFragment")
class RatingFragment @SuppressLint("ValidFragment") constructor
(var activity : Activity) : Fragment() {
    var ratingRecycler : RecyclerView? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view  = inflater.inflate(R.layout.fragment_rating, container, false)
        ratingRecycler = view.findViewById(R.id.ratingRecycler)
        setRecycler()
        return view
    }
    private fun setRecycler() {
        ratingRecycler!!.layoutManager = LinearLayoutManager(activity,
                LinearLayout.VERTICAL,
                false)
        ratingRecycler!!.adapter = RatingsAdapter()
        ratingRecycler!!.isFocusable = false
    }
}
