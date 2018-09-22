package com.local.virpa.virpa.enum

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

enum class OpenGallery {
    ID { override fun getValue() = 1000 },
    PROFILE { override fun getValue() = 1001 };

    abstract fun getValue() : Int
}
