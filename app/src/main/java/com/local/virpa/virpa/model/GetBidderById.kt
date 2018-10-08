package com.local.virpa.virpa.model

object GetBidderById {
    data class Result(
            var succeed :  Boolean,
            var data : Data
    )
    data class Data(
            var user : User,
            var createdAt : String,
            var initialMessage : String,
            var bidPrice : Int,
            var status : Int
    )
    data class User(
            var detail : Details,
            var profilePicture : ProfilePicture,
            var location : LocationInfo
    )
    data class ProfilePicture(
            var id : String,
            var name : String,
            var codeName : String,
            var extension : String,
            var filePath : String,
            var type : Int,
            var createdAt : String
    )
    data class LocationInfo(
            var latitude : Double,
            var longitude : Double,
            var address : String,
            var cityName: String,
            var state: String,
            var countryName : String,
            var postalCode : String
    )
    data class Details(
            var id : String,
            var userName : String,
            var email : String,
            var fullname : String,
            var mobileNumber : String,
            var followersCount : Int,
            var backgroundSummary : String,
            var createdAt : String,
            var updatedAt : String
    )
}