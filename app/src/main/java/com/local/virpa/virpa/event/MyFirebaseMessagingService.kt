package com.local.virpa.virpa.event

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.local.virpa.virpa.R
import com.local.virpa.virpa.activity.ThreadActivity
import com.local.virpa.virpa.local_db.DatabaseHandler

class MyFirebaseMessagingService : FirebaseMessagingService() {
    var CHANNEL_ID = "FIREBASE_MESSAGING"
    var num = 1
    override fun onMessageReceived(p0: RemoteMessage?) {

        var title = p0?.data!!["title"].toString()
        var message = p0.data!!["message"].toString()
        var jsonData = p0.data!!["data"].toString()
        var info = FirebaseNotify().fromJson(jsonData)

        var intent = Intent(this, ThreadActivity::class.java).apply {
            putExtra("bidderID" , info.bidderID)
            putExtra("feedID" , info.feedID)
            putExtra("feederID" , info.feederID)
            putExtra("threadID", info.threadID)
        }

        var pending : PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        createNotificationChannel()
        var mBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_logo)
                .setColor(Color.BLUE)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pending)
                .setAutoCancel(true)
                .build()
        var notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(num++, mBuilder)
    }


    override fun onDeletedMessages() {
        super.onDeletedMessages()
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "SAMPLE_CHANNEL_NAME"
            val descriptionText = "SAMPLE_CHANNEL_DESC"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}