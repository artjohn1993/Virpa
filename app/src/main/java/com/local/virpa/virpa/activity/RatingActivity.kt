package com.local.virpa.virpa.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.widget.LinearLayout
import com.local.virpa.virpa.R
import com.local.virpa.virpa.adapter.ListViewAdapter
import com.local.virpa.virpa.adapter.RatingsAdapter
import kotlinx.android.synthetic.main.activity_list_view.*
import kotlinx.android.synthetic.main.activity_rating.*
import kotlinx.android.synthetic.main.activity_setting.*

class RatingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rating)
        title = "Total Ratings"
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
        ratingScroll.smoothScrollTo(0,0)
    }
    private fun setRecycler() {
        ratingRecycler.layoutManager = LinearLayoutManager(this,
                LinearLayout.VERTICAL,
                false)
        ratingRecycler.adapter = RatingsAdapter()
        ratingRecycler.isFocusable = false
    }
}
