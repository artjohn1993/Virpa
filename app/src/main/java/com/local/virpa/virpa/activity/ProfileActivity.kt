package com.local.virpa.virpa.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.widget.LinearLayout
import com.local.virpa.virpa.R
import com.local.virpa.virpa.adapter.ProfileAdapter
import com.local.virpa.virpa.adapter.SettingsAdapter
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_setting.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        bind()
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> {
                this.finish()
            }
        }
        return true
    }
    private fun bind() {
        setRecycler()
        scrollTop()
    }
    private fun scrollTop() {
        profileScroll.smoothScrollTo(0,0)
    }
    private fun setRecycler() {
        profileRecycler.layoutManager = LinearLayoutManager(this,
                LinearLayout.VERTICAL,
                false)
        profileRecycler.adapter = ProfileAdapter()
    }
}
