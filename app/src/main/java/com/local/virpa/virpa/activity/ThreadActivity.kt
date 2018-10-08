package com.local.virpa.virpa.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.local.virpa.virpa.R
import com.local.virpa.virpa.api.VirpaApi
import com.local.virpa.virpa.local_db.DatabaseHandler
import com.local.virpa.virpa.model.GetBidderById
import com.local.virpa.virpa.presenter.ThreadPresenterClass
import com.local.virpa.virpa.presenter.ThreadView
import kotlinx.android.synthetic.main.activity_thread.*
import kotlinx.android.synthetic.main.layout_comment.*

class ThreadActivity : AppCompatActivity(), ThreadView {

    var bidderID = ""
    var feederID = ""
    var feed = ""
    var database = DatabaseHandler(this)
    private val apiServer by lazy {
        VirpaApi.create(this)
    }
    var presenter = ThreadPresenterClass(this, apiServer)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thread)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        title = "Message"
        getIntentData()
        checkUser()
        presenter.getBidderById(bidderID, feed)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> {
                this.finish()
            }
        }
        return true
    }

    override fun responseGetBidderById(data: GetBidderById.Result) {
        bidderNameMes.text = data.data.user.detail.fullname
        bidderMessageMes.text = data.data.initialMessage
        bidderPriceMes.text = data.data.bidPrice.toString()
        Glide.with(this)
                .load(data.data.user.profilePicture.filePath)
                .into(bidderPictureMes)
    }

    private fun getIntentData() {
        if (intent.extras != null) {
            bidderID = intent.getStringExtra("bidderID")
            feederID = intent.getStringExtra("feederID")
            feed = intent.getStringExtra("feedID")
        }
    }
    private fun checkUser() {
        var currentUser = database.readSignResult()[0].user.detail.id

        if (currentUser == bidderID) {
            infoCon.visibility = View.GONE
        }
        if (currentUser == feederID) {
            infoCon.visibility = View.VISIBLE
        }
    }
}
