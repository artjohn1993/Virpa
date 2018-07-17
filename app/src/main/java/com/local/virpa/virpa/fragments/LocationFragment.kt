package com.local.virpa.virpa.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.local.virpa.virpa.R
import com.guo.duoduo.randomtextview.RandomTextView
import kotlinx.android.synthetic.main.fragment_location.*
import com.local.virpa.virpa.activity.MainActivity
import android.content.Intent
import android.widget.ImageButton
import com.local.virpa.virpa.activity.HomeActivity
import org.jetbrains.anko.sdk25.coroutines.onContextClick
import org.jetbrains.anko.view


@SuppressLint("ValidFragment")
class LocationFragment @SuppressLint("ValidFragment") constructor
(val activity: HomeActivity) : Fragment() {

    val fragment = activity.supportFragmentManager.beginTransaction()
    var changeView : ImageButton? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_location, container, false)
        changeView = view.findViewById(R.id.changeView)
        changeView?.setOnClickListener {
            fragment.replace(R.id.homeFrameLayout, ImageListFragment()).commit()
        }

        Handler().postDelayed(Runnable {
            var array : ArrayList<String> = ArrayList()
            array.add("sample 1")
            array.add("sample 2")
            array.add("sample 3")
            array.add("sample 4")
            array.add("sample 5")
            array.add("sample 6")
            array.add("sample 7")
            array.add("sample 8")
            array.add("sample 9")
            array.add("sample 10")
            try{
                random_textview.keyWords.addAll(array)
                random_textview.show()
            }catch (e : Exception) {

            }

        }, 2 * 1000)

        return view
    }


}
