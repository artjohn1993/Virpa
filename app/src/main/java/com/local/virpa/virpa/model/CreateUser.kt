package com.local.virpa.virpa.model

object CreateUser {
    data class Result(
            var succeed : Boolean,
            var data : Data
    )
    data class Data(
            var id : String,
            var userName : String,
            var fullname : String,
            var mobileNumber : String,
            var createdAt : String,
            var updatedAt : String
    )
    data class Post(
            var fullname : String,
            var mobilenumber : String,
            var email : String,
            var password : String
    )
}