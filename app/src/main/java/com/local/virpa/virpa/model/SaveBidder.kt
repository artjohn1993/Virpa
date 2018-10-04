package com.local.virpa.virpa.model

object SaveBidder {
    data class Post(
            var feedId : String,
            var initialMessage : String
    )
    data class Result(
            var succeed : Boolean,
            var data : Data,
            var message : List<String>
    )
    data class  Data(
            var bidder :  Bidders
    )
    data class Bidders(
            var feedId : String,
            var userId : String,
            var createdAt : String,
            var initialMessage : String,
            var status : Int
    )
}