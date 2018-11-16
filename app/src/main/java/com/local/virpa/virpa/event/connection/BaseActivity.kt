package com.local.virpa.virpa.event.connection

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.local.virpa.virpa.event.ShowSnackBar

open class BaseActivity : AppCompatActivity(), ConnectivityReceiver.ConnectivityReceiverListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerReceiver(ConnectivityReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        if (!isConnected) {
            ShowSnackBar.present("You are now offline!", this)
        }
        else {
            ShowSnackBar.present("You are now online!", this)
        }
    }
}