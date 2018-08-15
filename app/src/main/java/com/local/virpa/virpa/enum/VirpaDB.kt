package com.local.virpa.virpa.enum

var publicToken : String? = null

 enum class VirpaDB {
    DATABASE_NAME { override fun getValue() = "Virpa" },
    TABLE_SESSION { override fun getValue() = "Session_Token" },
    TABLE_REFRESH { override fun getValue() = "Refresh_Token" },
    USER_INFO { override fun getValue() = "User_Info" };

    abstract fun getValue() : String
}
object Table {
    enum class Session {
        TOKEN { override fun getValue() = "token" },
        EXPIRED { override fun getValue() = "expiredAt" };

        abstract fun getValue() : String
    }

    enum class Refresh {
        TOKEN { override fun getValue() = "token" },
        EXPIRED { override fun getValue() = "expiredAt" };

        abstract fun getValue() : String
    }

    enum class UserInfo {
        ID { override fun getValue() = "id" },
        USERNAME { override fun getValue() = "userName" },
        EMAIL { override fun getValue() = "email" },
        FULLNAME { override fun getValue() = "fullname" },
        MOBILE_NUMBER { override fun getValue() = "mobileNumber" },
        CREATED_AT { override fun getValue() = "createdAt" },
        UPDATED_AT { override fun getValue() = "updatedAt" };

        abstract fun getValue() : String
    }
}


