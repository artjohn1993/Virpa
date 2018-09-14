package com.local.virpa.virpa.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.widget.LinearLayout
import com.local.virpa.virpa.R
import com.local.virpa.virpa.adapter.VisitedAdapter
import com.local.virpa.virpa.api.VirpaApi
import com.local.virpa.virpa.model.FeedByUser
import com.local.virpa.virpa.model.Follow
import com.local.virpa.virpa.model.UserList
import com.local.virpa.virpa.presenter.VisitedProfilePresenterClass
import com.local.virpa.virpa.presenter.VisitedProfileView
import com.squareup.moshi.Moshi
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_visited_profile.*

class VisitedProfileActivity : AppCompatActivity(), VisitedProfileView {

    private val apiServer by lazy {
        VirpaApi.create(this)
    }
    val presenter = VisitedProfilePresenterClass(this, apiServer)
    var userId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visited_profile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        title = "Visit"
        setRecycler()
        getIntentData()
        followWrapper.setOnClickListener {
            checkFollow()
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

    private fun setRecycler() {
        visitedProfileRecycler.layoutManager = LinearLayoutManager(this,
                LinearLayout.VERTICAL,
                false)
        visitedProfileRecycler.isFocusable = false
    }
    private fun getIntentData() {
        if (intent.extras != null) {
            val bundle = intent.extras
            val jsonData = intent.getStringExtra("userInfo")
            assignUserInfo(convertToObject(jsonData))
        }
    }
    private fun assignUserInfo(data : UserList.Users) {
        userName.text = data.fullname
        if (data.profilePicture != null) {
            Picasso.get().load(data.profilePicture[0]?.filePath).into(profilePicture)
        }
        if (data.isFollow != 0) {
            followText.text = "followed"
            followIcon.setImageResource(R.drawable.ic_person)
        }
        else {
            followText.text = "follow"
            followIcon.setImageResource(R.drawable.ic_person_add_black_24dp)
        }
        presenter.getUserFeed(data.userId)
        userId = data.userId
    }
    private fun convertToObject(data : String) :  UserList.Users {
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter<UserList.Users>(UserList.Users::class.java)
        return adapter.fromJson(data)
    }
    fun checkFollow() {
        if(followText.text.toString() == "follow") {
            followText.text = "followed"
            followIcon.setImageResource(R.drawable.ic_person)
            presenter.follow(Follow.Post(userId))
        }
        else {
            followText.text = "follow"
            followIcon.setImageResource(R.drawable.ic_person_add_black_24dp)
            presenter.unFollow(Follow.Post(userId))
        }
    }

    override fun responseUserFeed(data: FeedByUser.Result?) {
        if(data?.data != null) {
            visitedProfileRecycler?.adapter = VisitedAdapter(this, data)
        }
    }

}
