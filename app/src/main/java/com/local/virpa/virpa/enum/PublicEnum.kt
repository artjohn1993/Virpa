package com.local.virpa.virpa.enum


var myID = ""
enum class LoginFragment {
    SIGNIN,
    SIGNUP,
    FORGET_PASSWORD
}

enum class ActivityType {
    THREADING,
    PERSONAL_MESSAGE
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
enum class NotificationType {

    THREADING { override fun getValue() = "message_in_thread" },
    BIDDING { override fun getValue() = "bidding" };

    abstract fun getValue() : String
}
