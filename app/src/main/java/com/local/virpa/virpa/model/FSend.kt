package com.local.virpa.virpa.model

object FSend {
    data class Post(
            var to : String,
            var data : Data
    )
    data class Data(
            var title : String,
            var message : String,
            var data_type : String/*,
            var feedID : String,
            var myID : String,
            var bidderID : String,
            var feederID : String*/
    )
    data class Result(
            var success : Int,
            var failure : Int
    )
}