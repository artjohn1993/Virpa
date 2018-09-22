package com.local.virpa.virpa.model

object Location {

    data class Post(
            var latitude : Double,
            var longitude : Double,
            var address : String,
            var cityName: String,
            var state: String,
            var countryName : String,
            var postalCode : String
    )

    data class Result(
            var succeed: Boolean,
            var data : Data,
            var message : List<String>
    )
    data class Data(
            var location : LocationData
    )
    data class LocationData(
            var latitude : Double,
            var longitude : Double,
            var address : String,
            var cityName: String,
            var state: String,
            var countryName : String,
            var postalCode : Int
    )
}