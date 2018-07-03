package com.local.virpa.virpa.activity

import android.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.local.virpa.virpa.fragments.SignInFragment
import com.local.virpa.virpa.R
import com.local.virpa.virpa.event.LoginChangeFragment
import com.local.virpa.virpa.event.LoginEvent
import com.local.virpa.virpa.fragments.SignupFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    //region - Life Cycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        //fragment.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_in)
        changeFragment(SignInFragment())
    }

    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)

    }
    //endregion

    //region - Private Methods
    private fun changeFragment(data : android.support.v4.app.Fragment) {
        var fragment = supportFragmentManager.beginTransaction()
        fragment.setCustomAnimations(R.anim.abc_slide_in_bottom, android.R.animator.fade_out)
        fragment.replace(R.id.frameContainer, data).commit()
    }

    private fun nextActivity() {
        startActivity<HomeActivity>()
        finish()
    }
    //endregion

    //region - EventBus Method
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoginChangeFragment(event : LoginChangeFragment) {
        if(event.type == "signup") {
            changeFragment(SignupFragment())
        }
        else {
            changeFragment(SignInFragment())
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoginEvent(event : LoginEvent) {
        nextActivity()
    }
    //endregion

}

