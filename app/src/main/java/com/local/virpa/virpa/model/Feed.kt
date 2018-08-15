package com.local.virpa.virpa.model

object Feed {
    data class Result(
            var succeed : Boolean,
            var data : Data
    )
    data class Data(
            var feeds: List<Feeds>
    )
    data class Feeds(
            var feedId : String,
            var type : Int,
            var body : String,
            var budget : Int,
            var upVoteCounts : Int,
            var biddingCounts : Int,
            var createdAt : String,
            var updatedAt : String,
            var expiredAt : String,
            var status : Int,
            var coverPhotos : List<CoverPhotos>
    )
    data class CoverPhotos(
            var id : String,
            var name : String,
            var codeName : String,
            var extension : String,
            var filePath : String,
            var type : Int,
            var createdAt : String
    )
}