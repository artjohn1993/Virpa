package com.local.virpa.virpa.event

import com.local.virpa.virpa.enum.FragmentType

class Refresh {
    var type : FragmentType
    constructor(data : FragmentType) {
        this.type = data
    }
}