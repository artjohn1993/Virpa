package com.local.virpa.virpa.activity

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import com.local.virpa.virpa.fragments.PopulatedLocationFragment
import com.local.virpa.virpa.R
import com.local.virpa.virpa.event.GetLocationEvent
import com.local.virpa.virpa.fragments.EmptyLocationFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class SetLocationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_location)
        changeFragment(EmptyLocationFragment(this))
    }
    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }
    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
    private fun changeFragment(data : android.support.v4.app.Fragment) {
        var fragment = supportFragmentManager.beginTransaction()
        fragment.setCustomAnimations(R.anim.abc_fade_in, R.anim.fade_out)
        fragment.replace(R.id.setLocationFrame, data).commit()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onGetLocationEvent(event : GetLocationEvent) {
        changeFragment(PopulatedLocationFragment())
    }
}
