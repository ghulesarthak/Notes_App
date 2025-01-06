package com.example.todousing_firebase.Database

data class Note(
    var subject: String = "",
    var description: String = "",
    var key : String="",
    val timestamp: Long = System.currentTimeMillis()

)