package com.local.virpa.virpa.event

import com.local.virpa.virpa.enum.LoginFragment

class LoginChangeFragment {
    var type : LoginFragment

    constructor(type : LoginFragment) {
        this.type = type
    }

}