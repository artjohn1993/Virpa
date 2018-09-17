package com.local.virpa.virpa.model

object GetFollow {
    data class ResultFollowed(
            var succeed : Boolean,
            var data : Data,
            var message : List<String>
    )

    data class ResultFollower(
            var succeed : Boolean,
            var data : Data2,
            var message : List<String>
    )

    data class Data(
            var followed : List<Followed>
    )
    data class Data2(
            var followers : List<Followed>
    )

    data class Followed(
            var user: User,
            var followedAt : String,
            var updatedAt : String
    )
    data class User(
            var detail : Detail,
            var profilePicture : ProfilePicture?
    )
    data class Detail(
            var id : String,
            var userName : String,
            var email : String,
            var fullname : String,
            var mobileNumber : String,
            var followersCount : Int,
            var backgroundSummary : String,
            var createdAt : String,
            var updatedAt : String
    )
    data class ProfilePicture(
            var id : String,
            var name : String,
            var codeName : String,
            var extension : String,
            var filePath : String,
            var type : Int,
            var createdAt : String
    )
}