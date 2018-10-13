package com.local.virpa.virpa.event

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import com.local.virpa.virpa.enum.myID

class MyFirebaseInstanceService : FirebaseInstanceIdService() {

    override fun onTokenRefresh() {
        var token = FirebaseInstanceId.getInstance().getToken()
        updateToken(token)
    }
    private fun updateToken(token : String?) {
        FirebaseDatabase.getInstance().reference
                .child("user")
                .child(myID)
                .child("token")
                .setValue(token)
    }
}