package com.local.virpa.virpa.event

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(p0: RemoteMessage?) {

    }

    override fun onDeletedMessages() {
        super.onDeletedMessages()
    }
}