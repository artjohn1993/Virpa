package com.local.virpa.virpa.model

object MySkills {
    data class Result(
            var succeed : Boolean,
            var data : Data,
            var message : List<String>
    )
    data class Data(
            var skills : MutableList<Skill>
    )
    data class Skill(
            var id : Int,
            var name : String,
            var description : String
    )
}