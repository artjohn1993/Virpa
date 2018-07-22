package com.local.virpa.virpa.model

object SignIn {
    data class Request(
            var email : String,
            var password : String,
            var rememberMe : Boolean
    )
    data class Result(
            var succeed : Boolean,
            var data : Data
    )
    data class Data(
            var authorization : Authorization,
            var user : User

    )
    data class Authorization(
            var sessionToken : SessionToken,
            var refreshToken : RefreshToken
    )
    data class SessionToken(
            var token : String,
            var expiredAt : String
    )
    data class RefreshToken(
            var token : String,
            var expiredAt : String
    )
    data class User(
            var id : String,
            var userName : String,
            var email : String,
            var fullname : String,
            var mobileNumber : String,
            var createdAt : String,
            var updatedAt : String
    )
}