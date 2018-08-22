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
            var token : String,
            var expiredAt : String

    )
}