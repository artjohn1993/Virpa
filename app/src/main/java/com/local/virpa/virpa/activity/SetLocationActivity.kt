package com.local.virpa.virpa.activity

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.local.virpa.virpa.event.ShowSnackBar
import android.location.LocationManager
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.local.virpa.virpa.R
import com.local.virpa.virpa.api.VirpaApi
import com.local.virpa.virpa.enum.Table
import com.local.virpa.virpa.enum.VirpaDB
import com.local.virpa.virpa.local_db.DatabaseHandler
import com.local.virpa.virpa.presenter.LocationPresenterClass
import com.local.virpa.virpa.presenter.LocationView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_set_location.*


class SetLocationActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationChangeListener, LocationView {

     private val apiServer by lazy {
         VirpaApi.create(this)
     }
     var presenter = LocationPresenterClass(this,apiServer)
     var permissionFineLoc = android.Manifest.permission.ACCESS_FINE_LOCATION
     var REQUEST_CODE = 1
     var permissionArray = arrayOf(permissionFineLoc)
     private var compositeDisposable : CompositeDisposable = CompositeDisposable()
     lateinit var locationManager : LocationManager
     var location : Location? = null
     var latitude : Double = 0.0
     var longitude : Double = 0.0
     var address : String = ""
     var state : String = ""
     var countryName : String = ""
     var cityName : String = ""
     var postalCode : String = ""
     var googleMap : GoogleMap? = null
     var isLocationSaved : Boolean = false
     var db = DatabaseHandler(this)
     var dbLatitude : Double = 0.0
     var dbLongitude : Double = 0.0
     var hasNewLocation : Boolean = false



    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_location)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        title = "Set Location"
        fetchLocation()
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
        locateButton.setOnClickListener {
            googleMap?.isMyLocationEnabled = true
            googleMap?.uiSettings?.isMyLocationButtonEnabled = false
        }

    }

    override fun onPause() {
        super.onPause()
        try {
            mapView.onPause()
        } catch (e : Exception) {

        }

        compositeDisposable.clear()
    }

    override fun onStop() {
        super.onStop()
        try {
            mapView.onStop()
        } catch (e : Exception) {

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
                    if(mapView != null) {
                        mapView.onCreate(null)
                        mapView.onResume()
                        mapView.getMapAsync(this)
                    }
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
                if (hasNewLocation) {
                    var pinLocation = com.local.virpa.virpa.model.Location.Post(
                            latitude,
                            longitude,
                            address,
                            cityName,
                            state,
                            countryName,
                            postalCode
                    )
                    presenter.pinLocation(pinLocation)
                }
                else {
                    ShowSnackBar.present("No new location found", this)
                }
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
        if(googleMap != null) {
            this.googleMap = googleMap
            googleMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
            googleMap?.isMyLocationEnabled = false
            //googleMap?.uiSettings?.isMyLocationButtonEnabled = true
            googleMap?.setOnMyLocationChangeListener(this)

            if (googleMap == null) {
                ShowSnackBar.present("Sorry! unable to create map", this)
            }
            if (isLocationSaved) {
                var locationData = LatLng(dbLatitude, dbLongitude)
                this.googleMap?.addMarker(MarkerOptions().position(locationData).title("Saved Location").visible(true))
                this.googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(locationData, 16.0f))
            }
        }
    }


    @SuppressLint("MissingPermission")
    override fun onMyLocationChange(location: Location?) {
        var geoCoder = Geocoder(this)
        var info = geoCoder.getFromLocation(location?.latitude!!,location?.longitude,1)

        latitude = info[0].latitude
        longitude = info[0].longitude
        address = checkData(info[0].subLocality)
        state = checkData(info[0].adminArea)
        countryName = checkData(info[0].countryName)
        cityName = checkData(info[0].locality)
        postalCode = checkData(info[0].postalCode)

        if(dbLatitude != latitude && dbLongitude != longitude) {
            this.googleMap?.clear()
            var locationData = LatLng(latitude,longitude)
            this.googleMap?.addMarker(MarkerOptions().position(locationData).title("New Location").visible(true))
            this.googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(locationData, 16.0f))
            var preview : String = ""
            if (address != "") {
                preview = address + ", "
            }
            if (cityName != "") {
                preview = "$preview$cityName, "
            }
            if (countryName != "") {
                preview = "$preview$countryName"
            }
            locationPreview.text = preview
        }
        this.googleMap?.isMyLocationEnabled = false
        hasNewLocation = true

    }


    override fun responsePinLocation(data: com.local.virpa.virpa.model.Location.Result) {
        db.updateData(VirpaDB.TABLE_LOCATION.getValue(), Table.UserLocation.LATITUDE.getValue(), data.data.location.latitude.toString())
        db.updateData(VirpaDB.TABLE_LOCATION.getValue(), Table.UserLocation.LONGITUDE.getValue(), data.data.location.longitude.toString())
        db.updateData(VirpaDB.TABLE_LOCATION.getValue(), Table.UserLocation.ADDRESS.getValue(), data.data.location.address)
        db.updateData(VirpaDB.TABLE_LOCATION.getValue(), Table.UserLocation.CITY_NAME.getValue(), data.data.location.cityName)
        db.updateData(VirpaDB.TABLE_LOCATION.getValue(), Table.UserLocation.STATE.getValue(), data.data.location.state)
        db.updateData(VirpaDB.TABLE_LOCATION.getValue(), Table.UserLocation.COUNTRY_NAME.getValue(), data.data.location.countryName)
        db.updateData(VirpaDB.TABLE_LOCATION.getValue(), Table.UserLocation.POSTAL_CODE.getValue(), data.data.location.postalCode.toString())
        ShowSnackBar.present("Location Save", this)
    }
    fun checkData(data : String?) : String {
        if (data != null) {
            return data
        }
        else {
            return ""
        }
    }

    fun fetchLocation() {
        var result = db.readSignResult()

        if (result[0].user.location?.latitude!!.toDouble() == 0.0) {
            isLocationSaved = false
        } else {
            dbLongitude = result[0].user.location?.longitude!!.toDouble()
            dbLatitude = result[0].user.location?.latitude!!.toDouble()
            isLocationSaved = true

            var preview : String = ""
            if (result[0].user.location?.address != "") {
                preview = result[0].user.location?.address + ", "
            }
            if (result[0].user.location?.cityName != "") {
                preview += result[0].user.location?.cityName + ", "
            }
            if (result[0].user.location?.countryName != "") {
                preview += result[0].user.location?.countryName
            }
            locationPreview.text = preview
        }
    }

}




