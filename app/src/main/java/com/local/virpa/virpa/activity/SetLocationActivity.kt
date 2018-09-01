package com.local.virpa.virpa.activity

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.local.virpa.virpa.R
import com.local.virpa.virpa.event.GetLocationEvent
import com.local.virpa.virpa.fragments.EmptyLocationFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import com.local.virpa.virpa.event.ShowSnackBar
import org.ankit.gpslibrary.MyTracker
import android.test.mock.MockPackageManager







class SetLocationActivity : AppCompatActivity() {
    var permission = android.Manifest.permission.ACCESS_FINE_LOCATION
    var REQUEST_CODE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_location)
        changeFragment(EmptyLocationFragment(this))
        if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), REQUEST_CODE)
        }

    }
    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }
    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            REQUEST_CODE -> {
                if (grantResults.isEmpty()) {
                    ShowSnackBar.present("Permission denied", this)
                }
            }
        }
    }

    private fun changeFragment(data : android.support.v4.app.Fragment) {
        var fragment = supportFragmentManager.beginTransaction()
        fragment.setCustomAnimations(R.anim.abc_fade_in, R.anim.fade_out)
        fragment.replace(R.id.setLocationFrame, data).commit()
    }

    fun getLocation() {
        val tracker = MyTracker(this)
        println("latitude:"+tracker.latitude)
        println("longitude:"+tracker.longitude)
        println("address:"+tracker.address)
        println("cityName:"+tracker.cityName)
        println("countryCode:"+tracker.countryCode)
        println("countryName:"+tracker.countryName)
        println("ipAddress:"+tracker.ipAddress)
        println("macAddress:"+tracker.macAddress)
        println("state:"+tracker.state)
        println("zip:"+tracker.zip)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onGetLocationEvent(event : GetLocationEvent) {
        //changeFragment(PopulatedLocationFragment())
        getLocation()
    }
}
