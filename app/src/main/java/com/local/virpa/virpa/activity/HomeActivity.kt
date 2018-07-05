package com.local.virpa.virpa.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.local.virpa.virpa.R
import kotlinx.android.synthetic.main.activity_home.*
import android.support.annotation.NonNull
import android.support.design.widget.BottomNavigationView
import com.local.virpa.virpa.fragments.*


class HomeActivity : AppCompatActivity() {

    var currentfragment : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.elevation = 0F
        title = ""
        changeFragment(AccountFragment(), 0)

        navigationBar.setOnNavigationItemSelectedListener(
                BottomNavigationView.OnNavigationItemSelectedListener { item ->
                    when (item.itemId) {
                        R.id.sample1 -> {
                            changeFragment(AccountFragment(), 1)
                            currentfragment = 1
                        }
                        R.id.sample2 -> {
                            changeFragment(FeedFragment(), 2)
                            currentfragment = 2

                        }
                        R.id.sample3 -> {
                            changeFragment(LocationFragment(), 3)
                            currentfragment = 3

                        }
                        R.id.sample4 -> {
                            changeFragment(NotificationFragment(), 4)
                            currentfragment = 4

                        }
                        R.id.sample5 -> {
                            changeFragment(MessageFragment(), 5)
                            currentfragment = 5

                        }
                    }
                    true
                })
    }

    private fun changeFragment(data : android.support.v4.app.Fragment, selectedFragment : Int) {
        var fragment = supportFragmentManager.beginTransaction()

        if( currentfragment > selectedFragment) {
            fragment.setCustomAnimations(R.anim.slide_right, R.anim.fade_out)
        }
        else if(currentfragment < selectedFragment) {
            fragment.setCustomAnimations(R.anim.slide_left, R.anim.fade_out)
        }
        fragment.replace(R.id.homeFrameLayout, data).commit()
    }
}
