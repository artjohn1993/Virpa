package com.local.virpa.virpa.event

class RegisterEvent {
    var fullname : String
    var mobilenumber : String
    var email : String
    var password : String

    constructor(fullname : String,
                mobilenumber : String,
                email : String,
                password : String) {
        this.fullname = fullname
        this.mobilenumber = mobilenumber
        this.email = email
        this.password = password
    }
}