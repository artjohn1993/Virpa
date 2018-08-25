package com.local.virpa.virpa.model

object SaveFiles {
    data class Result(
            var succeed : Boolean,
            var data : Data,
            var message : List<String>

    )
    data class Data(
            var files : List<Files>
    )
    data class Files(
            var id : String,
            var name : String,
            var codeName : String,
            var extension : String,
            var filePath : String,
            var type : Int,
            var createdAt : String
    )
}