package com.local.virpa.virpa.event

import android.app.Activity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.local.virpa.virpa.api.FirebaseApi
import com.local.virpa.virpa.local_db.DatabaseHandler
import com.local.virpa.virpa.model.FSend
import com.local.virpa.virpa.presenter.FirebasePresenterClass
import com.local.virpa.virpa.presenter.FirebaseView

class CustomNotification(var activity : Activity) : FirebaseView {

    private val firebaseApiServer by lazy {
        FirebaseApi.create(activity)
    }
    val fpresenter = FirebasePresenterClass(this, firebaseApiServer)
    var database = DatabaseHandler(activity)

    override fun SendResponse(data: FSend.Result) {

    }
    private fun request(key : String, title : String, message : String, data : String) {
        var myInfo = database.readSignResult()[0]
        var postData = FSend.Data(
                title,
                message,
                data
        )
        var post = FSend.Post(
                key,
                postData
        )
        fpresenter.send(post)
    }

    fun sendNotification(toUserID : String, title : String, message : String, data : String) {
        var root = FirebaseDatabase.getInstance().reference
        var query = root.child("user")
                .child(toUserID)


        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                var sample = p0.child("token").value.toString()
                request(sample, title, message, data)
                println()
            }

        })
    }
}