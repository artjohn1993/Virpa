package com.local.virpa.virpa.event

import android.app.Activity
import android.os.Build
import android.support.annotation.RequiresApi
import com.google.firebase.database.FirebaseDatabase
import com.local.virpa.virpa.enum.ActivityType
import com.local.virpa.virpa.model.FirebaseModel
import com.squareup.moshi.Moshi
import java.time.LocalDateTime

class FirebaseNotify {
    var moshi = Moshi.Builder().build()
    var threadAdapter = moshi.adapter<FirebaseModel.data>(FirebaseModel.data::class.java)

    @RequiresApi(Build.VERSION_CODES.O)
    fun notify(toUserID : String, toUserName : String, jsonData : String, type : ActivityType, description : String, action : String) {
        var root = FirebaseDatabase.getInstance().reference.child("notification")
        var map = HashMap<String, String>()
        var temp = root.child(toUserID).push().key
        var info = fromJson(jsonData)
        //var currentDateTime = LocalDateTime.now()
        root.child(toUserID)
                .updateChildren(map as Map<String, Any>)


        var map2 = HashMap<String, String>()
        map2.put("activity", activityType(type))
        map2.put("data", jsonData)
        map2.put("description", description)
        map2.put("name", toUserName)
        map2.put("seen", "false")
        map2.put("time", "")
        map2.put("action", action)

        root.child(toUserID)
                .child(temp!!)
                .updateChildren(map2 as Map<String, Any>)
    }

    fun toJson(bidderID : String, feederID : String, feedID : String,thread : String) : String {
        var data  = FirebaseModel.data(bidderID, feederID, feedID, thread)
        var json = threadAdapter.toJson(data)
        return json
    }
    fun fromJson(data : String) : FirebaseModel.data {
        var data = threadAdapter.fromJson(data)
        return data
    }

    fun activityType(type : ActivityType) : String {
        var data = ""
        when(type) {
            ActivityType.THREADING -> {
                data = ActivityType.THREADING.getValue()
            }
            ActivityType.PERSONAL_MESSAGE -> {
                data = ActivityType.PERSONAL_MESSAGE.getValue()
            }
        }
        return data
    }

}