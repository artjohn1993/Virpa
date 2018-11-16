package com.local.virpa.virpa.enum

var publicToken : String? = null
var publicFKey : String? = null

 enum class VirpaDB {
    DATABASE_NAME { override fun getValue() = "Virpa" },
    TABLE_SESSION { override fun getValue() = "Session_Token" },
    TABLE_REFRESH { override fun getValue() = "Refresh_Token" },
    TABLE_LOCATION { override fun getValue() = "User_Location" },
    USER_INFO { override fun getValue() = "User_Info" },
    USER_DATA { override fun getValue() = "User_Data" };


    abstract fun getValue() : String
}

object Table {
    enum class UserData {
        FEED { override fun getValue() = "feed" },
        LOCATION { override fun getValue() = "location" },
        NOTIFICATION { override fun getValue() = "notification" };

        abstract fun getValue() : String
    }

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

    enum class UserLocation {
        LATITUDE { override fun getValue() = "latitude" },
        LONGITUDE { override fun getValue() = "longitude" },
        ADDRESS { override fun getValue() = "address" },
        CITY_NAME { override fun getValue() = "cityName" },
        STATE { override fun getValue() = "state" },
        COUNTRY_NAME { override fun getValue() = "countryName" },
        POSTAL_CODE { override fun getValue() = "postalCode" };

        abstract fun getValue() : String
    }

    enum class UserInfo {
        ID { override fun getValue() = "id" },
        USERNAME { override fun getValue() = "userName" },
        EMAIL { override fun getValue() = "email" },
        FULLNAME { override fun getValue() = "fullname" },
        MOBILE_NUMBER { override fun getValue() = "mobileNumber" },
        FOLLOWERS { override fun getValue() = "followersCount" },
        CREATED_AT { override fun getValue() = "createdAt" },
        UPDATED_AT { override fun getValue() = "updatedAt" },
        BACKGROUND_SUMMARY { override fun getValue() = "backgroundSummary" },
        FILE_PATH { override fun getValue() = "filePath" };

        abstract fun getValue() : String
    }
}


