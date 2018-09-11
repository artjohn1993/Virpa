package com.local.virpa.virpa.model

object UserList {

    data class Result(
            var succeed : Boolean,
            var data : Data,
            var message : List<String>
    )
    data class Data(
            var users : List<Users>
    )
    data class Users(
            var userId : String,
            var email : String,
            var fullname : String,
            var mobileNumber : String,
            var followersCount : Int,
            var backgroundSummary : String?,
            var createdAt : String,
            var updatedAt : String,
            var isFollow : Int,
            var isFollowing : Int,
            var profilePicture : List<ProfilePicture?>
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