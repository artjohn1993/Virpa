package com.local.virpa.virpa.model

object DeleteFiles {
    data class Post(
            var files : List<Files>
    )
    data class Files(
            var fileId : String
    )
    data class Result(
            var succeed : Boolean,
            var data : Data
    )
    data class Data(
            var files : List<Files2>
    )
    data class Files2(
            var id : String,
            var name : String,
            var codeName : String,
            var extension : String,
            var filePath : String,
            var type : Int,
            var createdAt : String
    )
}