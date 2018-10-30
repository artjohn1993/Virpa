package com.local.virpa.virpa.model

object FirebaseModel {
    
    data class data(
            var bidderID : String,
            var feederID : String,
            var feedID : String,
            var threadID : String
    )

    data class Response(
            var activity : String,
            var description : String,
            var name : String,
            var seen : String,
            var time : String,
            var intent : Intent
    )
    data class Intent(
            var bidderID : String,
            var feedID : String,
            var feederID : String,
            var threadID : String
    )
}