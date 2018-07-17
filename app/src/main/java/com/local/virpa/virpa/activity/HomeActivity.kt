package com.local.virpa.virpa.activity

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.local.virpa.virpa.R
import kotlinx.android.synthetic.main.activity_home.*
import android.support.annotation.NonNull
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.ActionBar
import android.util.Log
import android.view.Menu
import android.widget.Toolbar
import com.local.virpa.virpa.fragments.*
import kotlinx.android.synthetic.main.toolbar_layout.*


class HomeActivity : AppCompatActivity() {

    var currentfragment : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportActionBar?.elevation = 0F

        setSupportActionBar(customToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        changeFragment(FeedFragment(), 0)
        navigationBar.disableShiftMode()
        navigationBar.setOnNavigationItemSelectedListener(
                BottomNavigationView.OnNavigationItemSelectedListener { item ->
                    when (item.itemId) {
                        R.id.feed -> {
                            changeFragment(FeedFragment(), 1)
                            currentfragment = 1
                        }
                        R.id.location -> {
                            changeFragment(LocationFragment(this), 2)
                            currentfragment = 2
                        }
                        R.id.post -> {
                            changeFragment(PostFragment(), 3)
                            currentfragment = 3

                        }
                        R.id.notif -> {
                            changeFragment(NotificationFragment(), 4)
                            currentfragment = 4
                        }
                        R.id.message -> {
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
            fragment.setCustomAnimations(R.anim.abc_fade_in, R.anim.fade_out)
        }
        else if(currentfragment < selectedFragment) {
            fragment.setCustomAnimations(R.anim.abc_fade_in, R.anim.fade_out)
        }
        fragment.replace(R.id.homeFrameLayout, data).commit()
    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.navigation, menu)
        return super.onCreateOptionsMenu(menu)
    }*/

    @SuppressLint("RestrictedApi")
    fun BottomNavigationView.disableShiftMode() {
        val menuView = getChildAt(0) as BottomNavigationMenuView
        try {
            val shiftingMode = menuView::class.java.getDeclaredField("mShiftingMode")
            shiftingMode.isAccessible = true
            shiftingMode.setBoolean(menuView, false)
            shiftingMode.isAccessible = false
            for (i in 0 until menuView.childCount) {
                val item = menuView.getChildAt(i) as BottomNavigationItemView
                item.setShiftingMode(false)
                // set once again checked value, so view will be updated
                item.setChecked(item.itemData.isChecked)
            }
        } catch (e: NoSuchFieldException) {

        } catch (e: IllegalStateException) {

        }
    }
}
