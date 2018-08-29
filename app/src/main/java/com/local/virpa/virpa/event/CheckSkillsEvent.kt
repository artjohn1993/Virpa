package com.local.virpa.virpa.event

class CheckSkillsEvent {
    var id : Int
    var title : String
    var check : Boolean
    constructor(id : Int,title : String, check : Boolean) {
        this.id = id
        this.title = title
        this.check = check
    }
}