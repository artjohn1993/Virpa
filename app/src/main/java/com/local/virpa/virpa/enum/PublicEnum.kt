package com.local.virpa.virpa.enum


var myID = ""
enum class LoginFragment {
    SIGNIN,
    SIGNUP,
    FORGET_PASSWORD
}

enum class ActivityType {
    THREADING { override fun getValue() = "threading" },
    PERSONAL_MESSAGE { override fun getValue() = "personal_message" };

    abstract fun getValue() : String
}

enum class NotifAction {
    BID { override fun getValue() = "bid" },
    MESSAGE { override fun getValue() = "message" };

    abstract fun getValue() : String
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
    ID { override fun getValue() = 1003 },
    PROFILE { override fun getValue() = 1002 };

    abstract fun getValue() : Int
}
enum class NotificationType {

    THREADING { override fun getValue() = "message_in_thread" },
    BIDDING { override fun getValue() = "bidding" };

    abstract fun getValue() : String
}
