package com.local.virpa.virpa.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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

class BiddingActivity : AppCompatActivity(), BidderView, FirebaseView {

    private val apiServer by lazy {
        VirpaApi.create(this)
    }
    private val firebaseApiServer by lazy {
        FirebaseApi.create(this)
    }
    val presenter = BidderPresenterClass(this, apiServer)
    val fpresenter = FirebasePresenterClass(this, firebaseApiServer)
    private var compositeDisposable : CompositeDisposable = CompositeDisposable()
    var database = DatabaseHandler(this)
    var userToken = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        title = "Bidding"
        setRecycler()
        sendNotification()
        Glide.with(this)
                .load(database.readSignResult()[0].user.profilePicture!!.filePath)
                .into(profilePicture)

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
        commentRecycler.adapter = BiddingAdapter(this, data, getFeederId(), getCurrentUserId(), getFeedId())
    }

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

    }

    override fun SendResponse(data: FSend.Result) {

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

    private fun checkBidders(data: GetBidder.Result) {

        for (bidders : GetBidder.Bidders in data.data.bidders) {
            if (bidders.user.detail.id == getCurrentUserId()) {
                commentBox.visibility = View.GONE
            }
        }
        if(getFeederId() == getCurrentUserId()) {
            commentBox.visibility = View.GONE
        }
    }
    private fun sendNotification() {
        var myInfo = database.readSignResult()[0]
        var postData = FSend.Data(
                myInfo.user.detail.fullname,
        "Bid in your post",
        getFeedId(),
        myInfo.user.detail.id,
                myInfo.user.detail.id,
        getFeederId()
        )
        getUserToken()
        var post = FSend.Post(
            userToken,
            postData
        )
        fpresenter.send(post)
    }
    private fun getUserToken() {
        var token = ""
        var root = FirebaseDatabase.getInstance().reference
        var query = root.child("user")
                .child(getFeederId())


        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                userToken = p0.child("token").value.toString()
            }

        })
    }
}
