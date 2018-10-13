package com.local.virpa.virpa.enum


var myID = ""
enum class LoginFragment {
    SIGNIN,
    SIGNUP,
    FORGET_PASSWORD
}

enum class RequestError {
    UNAUTHORIZED,
    INVALID
}

enum class Follow {
    FOLLOWERS,
    FOLLOWED
}

enum class FragmentType {
    FEED,
    LOCATION
}

enum class OpenGallery {
    ID { override fun getValue() = 1002 },
    PROFILE { override fun getValue() = 1001 };

    abstract fun getValue() : Int
}
