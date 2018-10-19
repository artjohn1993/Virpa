package com.local.virpa.virpa.event

import android.app.Activity
import com.local.virpa.virpa.enum.ActivityType
import com.local.virpa.virpa.model.FirebaseModel
import com.squareup.moshi.Moshi

class FirebaseNotify {
    var moshi = Moshi.Builder().build()
    var threadAdapter = moshi.adapter<FirebaseModel.data>(FirebaseModel.data::class.java)

    fun notify(toUserID : String, toUserName : String, jsonData : String, type : ActivityType ) {

    }

    fun toJson(bidderID : String, feederID : String, feedID : String,thread : String) : String {
        var data  = FirebaseModel.data(bidderID, feederID, feedID, thread)
        var json = threadAdapter.toJson(data)
        return json
    }
}