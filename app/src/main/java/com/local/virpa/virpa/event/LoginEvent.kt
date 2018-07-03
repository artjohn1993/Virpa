package com.local.virpa.virpa.event

class LoginEvent {
    var username : String
    var password : String

    constructor(username : String, password : String) {
        this.username = username
        this.password = password
    }
}