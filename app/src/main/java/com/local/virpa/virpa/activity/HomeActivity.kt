package com.local.virpa.virpa.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.local.virpa.virpa.R
import kotlinx.android.synthetic.main.activity_home.*
import android.support.annotation.NonNull
import android.support.design.widget.BottomNavigationView



class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.elevation = 0F
        title = ""

        navigationBar.setOnNavigationItemSelectedListener(
                BottomNavigationView.OnNavigationItemSelectedListener { item ->
                    when (item.itemId) {
                        R.id.sample1 -> {

                        }
                        R.id.sample2 -> {

                        }
                        R.id.sample3 -> {

                        }
                        R.id.sample4 -> {

                        }
                        R.id.sample5 -> {

                        }
                    }
                    true
                })
    }
}
