package com.local.virpa.virpa.event

import android.graphics.Bitmap

class PostEvent {
    var type : String
    var body : String
    var budget : Double
    var path : String?

    constructor( type : String,
                body : String,
                budget : Double,
                 path : String?) {
        this.type = type
        this.body = body
        this.budget = budget
        this.path = path
    }
}