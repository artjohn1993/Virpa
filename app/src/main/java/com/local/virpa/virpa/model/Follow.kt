package com.local.virpa.virpa.model

object Follow {
    data class Post(
            var FollowedId : String
    )

    data class Result(
            var succeed : Boolean,
            var data : Data,
            var message : List<String>
    )
    data class Data(
            var followedId : String,
            var followedAt : String,
            var updatedAt : String
    )
}