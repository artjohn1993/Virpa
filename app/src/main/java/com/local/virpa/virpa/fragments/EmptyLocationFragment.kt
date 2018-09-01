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
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.provider.Settings
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.test.mock.MockPackageManager
import com.local.virpa.virpa.event.ShowSnackBar
import org.ankit.gpslibrary.MyTracker


@SuppressLint("ValidFragment")
class EmptyLocationFragment(var activity : Activity) : Fragment() {
    var getLocation : Button? = null

    private val REQUEST_CODE = 1
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_empty_location, container, false)
        getLocation = view.findViewById(R.id.getLocationButton)

        getLocation?.setOnClickListener {
            EventBus.getDefault().post(GetLocationEvent())
        }


        return view
    }



}
