package com.local.virpa.virpa.model

object SaveFeed {
    data class Result(
            var succeed : Boolean,
            var data : Data
    )
    data class Data(
            var feeds: Feeds,
            var coverPhoto : CoverPhoto
    )
    data class Feeds(
            var feedId : String,
            var type : Int,
            var body : String,
            var budget : Int,
            var createdAt : String,
            var expiredAt : String
    )
    data class CoverPhoto(
            var id : String,
            var name : String,
            var codeName : String,
            var extension : String,
            var filePath : String,
            var type : Int,
            var createdAt : String
    )
}