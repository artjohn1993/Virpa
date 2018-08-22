package com.local.virpa.virpa.event

import android.graphics.Bitmap

class PostEvent {
    var type : String
    var body : String
    var budget : String
    var image : Bitmap?

    constructor( type : String,
                body : String,
                budget : String,
                image : Bitmap) {
        this.type = type
        this.body = body
        this.budget = budget
        this.image = image
    }
}