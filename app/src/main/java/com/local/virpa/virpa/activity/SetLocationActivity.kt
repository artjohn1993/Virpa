package com.local.virpa.virpa.activity

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.local.virpa.virpa.R
import com.local.virpa.virpa.event.ShowSnackBar
import android.location.LocationManager
import android.test.mock.MockPackageManager
import android.view.MenuItem
import com.alirezaashrafi.library.MapType
import kotlinx.android.synthetic.main.activity_set_location.*


class SetLocationActivity : AppCompatActivity() {

    var permissionFineLoc = android.Manifest.permission.ACCESS_FINE_LOCATION
    var permissionCoarseLoc = android.Manifest.permission.ACCESS_COARSE_LOCATION
    var REQUEST_CODE = 1
    var permissionArray = arrayOf(permissionFineLoc)
    lateinit var locationManager : LocationManager
    var location : Location? = null
    var longitude : Double = 0.0
    var latitude : Double = 0.0
    var interval : Float = 0F

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_location)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        if(ContextCompat.checkSelfPermission(this, permissionFineLoc) != MockPackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissionArray, REQUEST_CODE)
        }
        else {
            getLocation()
        }

    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            REQUEST_CODE -> {
                if (grantResults.isEmpty()) {
                    ShowSnackBar.present("Permission denied", this)
                }
                else {
                    getLocation()
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    @SuppressLint("MissingPermission")
    fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        locationTitle?.text = "latitude : " + this.location?.latitude.toString() +  "   longitude : " + this.location?.longitude.toString()

        val locationListener: LocationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                latitude = location.latitude
                longitude = location.longitude
                println("latitude : " + location.latitude)
                println("longitude : " + location.longitude)
                locationTitle?.text = "latitude : " + location.latitude +  "   longitude : " + location.longitude
                //locationView.setLocation(location)
            }
            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {
            }
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,2000,interval,locationListener)
    }

}


