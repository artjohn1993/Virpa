package com.local.virpa.virpa.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.local.virpa.virpa.R
import com.local.virpa.virpa.event.GetLocationEvent
import org.greenrobot.eventbus.EventBus
import java.util.jar.Manifest
import android.Manifest.permission
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.local.virpa.virpa.event.ShowSnackBar
import org.ankit.gpslibrary.MyTracker


@SuppressLint("ValidFragment")
class EmptyLocationFragment(var activity : Activity) : Fragment() {
    var getLocation : Button? = null
    var permission = android.Manifest.permission.ACCESS_FINE_LOCATION
    var REQUEST_CODE = 1
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_empty_location, container, false)
        getLocation = view.findViewById(R.id.getLocationButton)

        getLocation?.setOnClickListener {
            getLocation()
        }

        if(ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(permission), REQUEST_CODE)
        }
        else {

        }

        return view
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            REQUEST_CODE -> {
                if (grantResults.isEmpty()) {
                    ShowSnackBar.present("Permission denied", activity)
                }
            }
        }
    }

    fun getLocation() {
        val tracker = MyTracker(activity)
        println(tracker.latitude)
        println(tracker.longitude)
        EventBus.getDefault().post(GetLocationEvent())
    }

}
