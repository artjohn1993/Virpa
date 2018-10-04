package com.local.virpa.virpa.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import com.local.virpa.virpa.R
import com.local.virpa.virpa.adapter.CommentAdapter
import com.local.virpa.virpa.adapter.SettingsAdapter
import com.local.virpa.virpa.api.VirpaApi
import com.local.virpa.virpa.local_db.DatabaseHandler
import com.local.virpa.virpa.model.GetBidder
import com.local.virpa.virpa.model.SaveBidder
import com.local.virpa.virpa.presenter.BidderPresenterClass
import com.local.virpa.virpa.presenter.BidderView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_comment.*
import kotlinx.android.synthetic.main.activity_setting.*

class BiddingActivity : AppCompatActivity(), BidderView {

    private val apiServer by lazy {
        VirpaApi.create(this)
    }
    val presenter = BidderPresenterClass(this, apiServer)
    private var compositeDisposable : CompositeDisposable = CompositeDisposable()
    var database = DatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        title = "Bidding"
        setRecycler()

        postText.setOnClickListener {
            if (postEditText.text.toString() != "") {
                var data = SaveBidder.Post(
                        getFeedId(),
                        postEditText.text.toString()
                )
                presenter.saveBid(data)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> {
                this.finish()
            }
        }
        return true
    }

    override fun responseGetBidder(data: GetBidder.Result) {
        checkBidders(data)
        commentRecycler.adapter = CommentAdapter(this, data)
    }

    override fun responseSaveBid(data: SaveBidder.Result) {
        presenter.getBidders(getFeedId())
    }

    private fun setRecycler() {
        commentRecycler.layoutManager = LinearLayoutManager(this,
                LinearLayout.VERTICAL,
                false)
        presenter.getBidders(getFeedId())

    }

    private fun getFeedId() : String {
        if (intent.extras != null) {
            return intent.getStringExtra("feedID")
        }
        else {
            return ""
        }
    }

    private fun checkBidders(data: GetBidder.Result) {
        var result = database.readSignResult()

        for (bidders : GetBidder.Bidders in data.data.bidders) {
            if (bidders.user.detail.id == result[0].user.detail.id) {
                commentBox.visibility = View.GONE
            }
        }
    }
}
