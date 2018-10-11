package com.local.virpa.virpa.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.local.virpa.virpa.R
import com.local.virpa.virpa.adapter.FTAdapter
import com.local.virpa.virpa.api.VirpaApi
import com.local.virpa.virpa.event.ShowSnackBar
import com.local.virpa.virpa.local_db.DatabaseHandler
import com.local.virpa.virpa.model.FeedThread
import com.local.virpa.virpa.model.GetBidderById
import com.local.virpa.virpa.presenter.ThreadPresenterClass
import com.local.virpa.virpa.presenter.ThreadView
import kotlinx.android.synthetic.main.activity_thread.*
import kotlinx.android.synthetic.main.layout_comment.*
import java.util.ArrayList

class ThreadActivity : AppCompatActivity(), ThreadView {

    var bidderID = ""
    var feederID = ""
    var feed = ""
    var threadID = ""
    var database = DatabaseHandler(this)
    private val apiServer by lazy {
        VirpaApi.create(this)
    }
    var presenter = ThreadPresenterClass(this, apiServer)
    var dataArray : ArrayList<FeedThread.Message> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thread)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        title = "Message"
        getIntentData()
        checkUser()
        setRecycler()
        presenter.getBidderById(bidderID, feed)

        commentButton.setOnClickListener {
            if (commentEdit.text.toString() != "") {
                sendMessage()
            }
        }

        var sample = FirebaseDatabase.getInstance().reference
                .child(getString(R.string.feed_thread))
                .child(feed)
                .child(threadID)

        sample.addChildEventListener(object : ChildEventListener {
                    override fun onCancelled(p0: DatabaseError?) {

                    }

                    override fun onChildMoved(p0: DataSnapshot?, p1: String?) {

                    }

                    override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
                        setData(p0)
                    }

                    override fun onChildAdded(p0: DataSnapshot?, p1: String?) {
                        setData(p0)
                    }

                    override fun onChildRemoved(p0: DataSnapshot?) {

                    }

                })

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
            threadID = intent.getStringExtra("threadID")
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
    private fun sendMessage() {
        var myInfo = database.readSignResult()[0]
        var root = FirebaseDatabase.getInstance().reference
        var map = HashMap<String, String>()


        map.put(getString(R.string.thread_message), commentEdit.text.toString())
        map.put(getString(R.string.thread_name), myInfo.user.detail.fullname)
        map.put(getString(R.string.thread_email), myInfo.user.detail.email)
        map.put(getString(R.string.thread_user_id), myInfo.user.detail.id)

        var map2 = HashMap<String, String>()

        var tempKey = root.child(getString(R.string.feed_thread))
                .child(feed)
                .push().key

        root.child(getString(R.string.feed_thread))
                .child(feed)
                .child(threadID)
                .updateChildren(map2 as Map<String, Any>?)

        var messageRoot = FirebaseDatabase.getInstance().reference
                .child(getString(R.string.feed_thread))
                .child(feed)
                .child(threadID)
                .child(tempKey)

        messageRoot.updateChildren(map as Map<String, Any>?).addOnCompleteListener {
            commentEdit.setText("")
        }

    }

    private fun setRecycler() {
        FTRecycler.layoutManager = LinearLayoutManager(this,
                LinearLayout.VERTICAL,
                false)
    }

    private fun setData(data : DataSnapshot?) {
        var i = data!!.children.iterator()

        while (i.hasNext()) {
            var email = i.next().value.toString()
            var message = i.next().value.toString()
            var name = i.next().value.toString()
            var userID = i.next().value.toString()

            var data = FeedThread.Message(
                    email,
                    message,
                    name,
                    userID
            )
            dataArray.add(data)
        }
        FTRecycler.adapter = FTAdapter(dataArray, database.readSignResult()[0].user.detail.id)
    }
}
