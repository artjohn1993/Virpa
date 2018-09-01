package com.local.virpa.virpa.model

object CreateUser {
    data class Result(
            var succeed : Boolean,
            var data : Data,
            var message : List<String>
    )
    data class Data(
            var user : User
    )
    data class User(
            var detail : Detail,
            var profilePicture : ProfilePicture
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
    data class Post(
            var fullname : String,
            var mobilenumber : String,
            var email : String,
            var password : String
    )
}