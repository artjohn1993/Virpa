package com.local.virpa.virpa.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.widget.LinearLayout
import com.local.virpa.virpa.R
import com.local.virpa.virpa.adapter.ListViewAdapter
import com.local.virpa.virpa.adapter.SettingsAdapter
import kotlinx.android.synthetic.main.activity_list_view.*
import kotlinx.android.synthetic.main.activity_setting.*

class ListViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)
        title = "Total Views"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        setRecycler()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> {
                this.finish()
            }
        }
        return true
    }

    private fun setRecycler() {
        totalViewRecycler.layoutManager = LinearLayoutManager(this,
                LinearLayout.VERTICAL,
                false)
        totalViewRecycler.adapter = ListViewAdapter()
        totalViewRecycler.isFocusable = false
    }
}
