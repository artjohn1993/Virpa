package com.local.virpa.virpa.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.alirezaashrafi.library.GoogleMapView
import com.local.virpa.virpa.R


class PopulatedLocationFragment : Fragment() {

    var view : GoogleMapView? = null
    var title : TextView? = null
    var save : Button? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_populated_location, container, false)
        return view
    }

}
