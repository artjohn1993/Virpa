package com.local.virpa.virpa.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.widget.LinearLayout
import com.local.virpa.virpa.R
import com.local.virpa.virpa.adapter.FollowAdapter
import com.local.virpa.virpa.api.VirpaApi
import com.local.virpa.virpa.model.GetFollow
import com.local.virpa.virpa.presenter.FollowPresenterClass
import com.local.virpa.virpa.presenter.FollowView
import kotlinx.android.synthetic.main.activity_follow.*


class FollowActivity : AppCompatActivity(), FollowView {

    private val apiServer by lazy {
        VirpaApi.create(this)
    }
    val presenter = FollowPresenterClass(this, apiServer)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_follow)
        title = "Follow"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        setRecycler()
        presenter.getFollowed()
        navigationBarFollow.setOnNavigationItemSelectedListener(
                BottomNavigationView.OnNavigationItemSelectedListener { item ->
                    when (item.itemId) {
                        R.id.following -> {
                            presenter.getFollowed()
                        }
                        R.id.follower -> {
                            presenter.getFollower()
                        }
                    }
                    true
                })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun responseFollowed(data: GetFollow.ResultFollowed) {
        followRecyclerView.adapter = FollowAdapter(null, data)
    }

    override fun responseFollower(data: GetFollow.ResultFollower) {
        followRecyclerView.adapter = FollowAdapter(data, null)
    }

    fun setRecycler() {
        followRecyclerView.layoutManager = LinearLayoutManager(this,
                LinearLayout.VERTICAL,
                false)
    }
}
