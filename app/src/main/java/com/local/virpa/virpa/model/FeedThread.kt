package com.local.virpa.virpa.model

object FeedThread {

    data class Message(
            var email : String,
            var message : String,
            var name : String,
            var id : String
    )
}