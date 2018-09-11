package com.local.virpa.virpa.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.widget.LinearLayout
import com.local.virpa.virpa.R
import com.local.virpa.virpa.adapter.VisitedAdapter
import com.local.virpa.virpa.model.UserList
import com.squareup.moshi.Moshi
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_visited_profile.*

class VisitedProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visited_profile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        title = "Visit"
        setRecycler()
        getIntentData()
        visitedScroll.smoothScrollTo(0,0)
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
        visitedProfileRecycler?.adapter = VisitedAdapter(this)
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
    }
    private fun convertToObject(data : String) :  UserList.Users {
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter<UserList.Users>(UserList.Users::class.java)
        return adapter.fromJson(data)
    }

}
