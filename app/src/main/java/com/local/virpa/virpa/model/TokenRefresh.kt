package com.local.virpa.virpa.model

object TokenRefresh {
    data class Post(
            var email : String,
            var tokenResource : TokenResource
    )
    data class TokenResource(
            var type : String,
            var token : String
    )
    data class Result(
            var succeed : Boolean,
            var data : Data,
            var message : List<String>
    )
    data class Data(
            var authorization : Authorization,
            var user : User

    )
    data class Authorization(
            var token : String,
            var expiredAt : String
    )

    data class User(
            var detail : Detail
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
}