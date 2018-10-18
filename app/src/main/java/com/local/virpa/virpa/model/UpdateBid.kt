package com.local.virpa.virpa.model

object UpdateBid {
    data class Negotiate(
            var bidId : String,
            var initialMessage : String,
            var bidPrice : String
    )
    data class Accept(
            var bidId : String
    )
    data class Close(
            var bidId : String
    )
    data class Result(
            var succeed : Boolean,
            var data : Data
    )
    data class Data(
            var bidder : Bidder
    )
    data class Bidder(
            var bidId : String,
            var feedId : String,
            var userId : String,
            var createdAt : String,
            var initialMessage : String,
            var bidPrice : Int,
            var status : Int
    )
}