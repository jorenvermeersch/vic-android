package com.example.vic.database.entities

data class Credentials(
    var username: String,
    var passwordHash: String,
    var role: String
)
