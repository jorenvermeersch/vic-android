package com.example.vic.domain.entities

data class Credentials(
    var username: String,
    var passwordHash: String,
    var role: String
)
