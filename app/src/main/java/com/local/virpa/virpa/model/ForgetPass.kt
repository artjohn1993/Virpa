package com.local.virpa.virpa.model

object ForgetPass {
    data class Post(
            var email : String
    )
    data class Result(
            var succeed : Boolean,
            var data : Data
    )
    data class Data(
            var userId : String,
            var token : String
    )
}