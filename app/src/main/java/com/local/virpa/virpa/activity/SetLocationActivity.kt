package com.local.virpa.virpa.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.local.virpa.virpa.R
import com.local.virpa.virpa.event.ShowSnackBar
import android.location.LocationManager
import android.os.Build
import android.support.annotation.RequiresApi
import android.test.mock.MockPackageManager
import android.view.Menu
import android.view.MenuItem
import com.alirezaashrafi.library.MapType
import com.developers.imagezipper.ImageZipper
import com.example.easywaylocation.EasyWayLocation
import com.example.easywaylocation.Listener
import com.google.android.gms.common.api.Api
import com.google.android.gms.common.api.GoogleApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.OnCompleteListener
import com.local.virpa.virpa.api.VirpaApi
import com.local.virpa.virpa.model.SaveFiles
import com.local.virpa.virpa.presenter.LocationPresenterClass
import com.local.virpa.virpa.presenter.LocationView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_set_location.*
import org.ankit.gpslibrary.MyTracker
import java.io.File


class SetLocationActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationChangeListener, LocationView {

    private val apiServer by lazy {
        VirpaApi.create(this)
    }
    var presenter = LocationPresenterClass(this,apiServer)
    var permissionFineLoc = android.Manifest.permission.ACCESS_FINE_LOCATION
    var permissionCoarseLoc = android.Manifest.permission.ACCESS_COARSE_LOCATION
    var REQUEST_CODE = 1
    private var compositeDisposable : CompositeDisposable = CompositeDisposable()
    var permissionArray = arrayOf(permissionFineLoc)
    lateinit var locationManager : LocationManager
    var location : Location? = null
    var latitude : Double = 0.0
    var longitude : Double = 0.0
    var subLocality : String = ""
    var adminArea : String = ""
    var countryName : String = ""
    var locality : String = ""
    var postalCode : String = ""


    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_location)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        title = "Set Location"

        if(ContextCompat.checkSelfPermission(this, permissionFineLoc) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissionArray, REQUEST_CODE)
        }
        else {
            if(mapView != null) {
                mapView.onCreate(null)
                mapView.onResume()
                mapView.getMapAsync(this)
            }
        }

    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
        compositeDisposable.clear()
    }

    override fun onStop() {
        super.onStop()
        mapView.onDestroy()
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
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_info_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> {
                finish()
            }
            R.id.saveData -> {
                var pinLocation = com.local.virpa.virpa.model.Location.Post(
                        latitude,
                        longitude,
                        subLocality,
                        adminArea,
                        countryName,
                        locality,
                        postalCode
                )
                presenter.pinLocation(pinLocation)

            }
        }
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap?) {
        MapsInitializer.initialize(this)    
        googleMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
        googleMap?.isMyLocationEnabled = true
        googleMap?.uiSettings?.isMyLocationButtonEnabled = true
        googleMap?.setOnMyLocationChangeListener(this)
        if (googleMap == null) {
            ShowSnackBar.present("Sorry! unable to create map", this)
        }
    }

    override fun onMyLocationChange(location: Location?) {
        var geoCoder = Geocoder(this)
        var info = geoCoder.getFromLocation(location?.latitude!!,location?.longitude,1)
        latitude = info[0].latitude
        longitude = info[0].longitude
        subLocality = info[0].subLocality
        adminArea = info[0].adminArea
        countryName = info[0].countryName
        locality = info[0].locality
        postalCode = info[0].postalCode
    }

    override fun responsePinLocation(data: com.local.virpa.virpa.model.Location.Result) {
        ShowSnackBar.present("Location Save", this)
    }

}




