package com.local.virpa.virpa.model

object UpdateUser {
    data class Post(
            var userid : String,
            var fullname: String,
            var mobilenumber : String,
            var backgroundSummary: String
    )

    data class Result(
            var succeed : Boolean,
            var data : Data,
            var message : List<String>
    )

    data class Data(
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
            var updatedAt  : String
    )
}