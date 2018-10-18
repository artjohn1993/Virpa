package com.local.virpa.virpa.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.local.virpa.virpa.R
import com.local.virpa.virpa.fragments.PicturesFragment
import com.local.virpa.virpa.fragments.RatingFragment
import com.local.virpa.virpa.fragments.VideoFragment
import kotlinx.android.synthetic.main.activity_portfolio.*

class PortfolioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portfolio)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        title = "Portfolio"
        changeFragment(RatingFragment(this))

        portNavigationBar.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.rating -> {
                    changeFragment(RatingFragment(this))
                }
                R.id.image -> {
                    changeFragment(PicturesFragment(this))
                }
                R.id.video -> {
                    changeFragment(VideoFragment(this))
                }
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> {
                this.finish()
            }
        }
        return true
    }

    private fun changeFragment(data : android.support.v4.app.Fragment) {
        var fragment = supportFragmentManager.beginTransaction()

        fragment.setCustomAnimations(R.anim.abc_fade_in, R.anim.fade_out)
        fragment.replace(R.id.portFrameLayout, data).commit()
    }
}
