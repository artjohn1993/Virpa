package com.local.virpa.virpa.activity

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.local.virpa.virpa.R
import com.local.virpa.virpa.adapter.BiddingAdapter
import com.local.virpa.virpa.api.FirebaseApi
import com.local.virpa.virpa.api.VirpaApi
import com.local.virpa.virpa.enum.ActivityType
import com.local.virpa.virpa.event.CustomNotification
import com.local.virpa.virpa.event.FirebaseNotify
import com.local.virpa.virpa.event.ShowSnackBar
import com.local.virpa.virpa.local_db.DatabaseHandler
import com.local.virpa.virpa.model.FSend
import com.local.virpa.virpa.model.GetBidder
import com.local.virpa.virpa.model.SaveBidder
import com.local.virpa.virpa.presenter.BidderPresenterClass
import com.local.virpa.virpa.presenter.BidderView
import com.local.virpa.virpa.presenter.FirebasePresenterClass
import com.local.virpa.virpa.presenter.FirebaseView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_comment.*
import java.util.*

class BiddingActivity : AppCompatActivity(), BidderView{

    private val apiServer by lazy {
        VirpaApi.create(this)
    }
    val presenter = BidderPresenterClass(this, apiServer)
    private var compositeDisposable : CompositeDisposable = CompositeDisposable()
    var database = DatabaseHandler(this)
    var userToken = ""
    var customNotification = CustomNotification(this)
    var notify = FirebaseNotify()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        title = "Bidding"
        setRecycler()
        checkCurrentUser()
        refresh(true)
        if(database.readSignResult()[0].user.profilePicture!!.filePath != "") {
            Glide.with(this)
                    .load(database.readSignResult()[0].user.profilePicture!!.filePath)
                    .into(profilePicture)
        }

        postText.setOnClickListener {
            if (postEditText.text.toString() != "") {
                var data = SaveBidder.Post(
                        getFeedId(),
                        postEditText.text.toString(),
                        postPriceEditText.text.toString()
                )
                presenter.saveBid(data)
            }
        }
        bidRefresh.setOnRefreshListener {
            presenter.getBidders(getFeedId())
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
        refresh(false)
        commentRecycler.adapter = BiddingAdapter(this, data, getFeederId(), getCurrentUserId(), getFeedId())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun responseSaveBid(data: SaveBidder.Result) {

        presenter.getBidders(getFeedId())
        var root = FirebaseDatabase.getInstance().reference
        var threadID = data.data.bidder.bidId
        val mapFeed = HashMap<String, String>()
        mapFeed.put(getFeedId(),"")
        val mapthread = HashMap<String, String>()
        mapthread.put(threadID,"")
        root.child(getString(R.string.feed_thread))
                .updateChildren((mapFeed as Map<String, Any>?)!!)

        root.child(getString(R.string.feed_thread))
                .child(getFeedId())
                .updateChildren((mapthread as Map<String, Any>?)!!)


        var json = notify.toJson(data.data.bidder.userId,
                getFeederId(),
                getFeedId(),
                data.data.bidder.bidId)

        notify.notify(getFeederId(),
                database.readSignResult()[0].user.detail.fullname,
                json,
                ActivityType.THREADING,
                "Bid in your post"
        )

        customNotification.sendNotification(getFeederId(),
                database.readSignResult()[0].user.detail.fullname,
                data.data.bidder.initialMessage,
                json
        )

    }

    override fun responseGetBidderNull(data: String) {
        checkCurrentUser()
        refresh(false)
        ShowSnackBar.present(data,this)
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
    private fun getFeederId() : String {
        if (intent.extras != null) {
            return intent.getStringExtra("feederID")
        }
        else {
            return ""
        }
    }
    private fun getCurrentUserId() : String {
        return database.readSignResult()[0].user.detail.id
    }

    private fun checkCurrentUser() {
        if(getCurrentUserId() == getFeederId()) {
            commentBox.visibility = View.GONE
        }
    }
    private fun checkBidders(data: GetBidder.Result) {

        for (bidders : GetBidder.Bidders in data.data!!.bidders) {
            if (bidders.user.detail.id == getCurrentUserId()) {
                commentBox.visibility = View.GONE
            }
        }
        checkCurrentUser()
    }
    private fun refresh(data : Boolean) {
        bidRefresh.isRefreshing = data
    }

}
