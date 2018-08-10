package com.local.virpa.virpa.model

object SignOut {

    data class POST(
            var email : String
    )
    data class Result(
            var succeed : Boolean,
            var message : List<String>
    )
}