package com.local.virpa.virpa.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.local.virpa.virpa.R

class LocationView1Fragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_location_view1, container, false)
        val random = view.findViewById<com.guo.duoduo.randomtextview.RandomTextView>(R.id.random_textview)
        var array : ArrayList<String> = ArrayList()
        array.add("sample 1")
        array.add("sample 2")
        array.add("sample 3")
        array.add("sample 4")
        array.add("sample 5")
        array.add("sample 6")
        array.add("sample 7")
        array.add("sample 8")
        random.keyWords.addAll(array)
        random.show()


        return view
    }
}
