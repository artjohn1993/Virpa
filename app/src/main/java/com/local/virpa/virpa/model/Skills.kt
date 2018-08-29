package com.local.virpa.virpa.model

object Skills {
    data class Result(
            var succeed : Boolean,
            var data : Data,
            var message : List<String>
    )
    data class Data(
            var skills : List<Skill>
    )
    data class Skill(
            var id : Int,
            var name : String,
            var description : String,
            var createdAt : String,
            var updatedAt : String
    )
}