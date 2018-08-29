package com.local.virpa.virpa.model

object SaveMySkills {
    data class Post(
            var skills : List<SkillsSet>
    )
    data class SkillsSet(
        var id : Int,
        var name : String
    )
}