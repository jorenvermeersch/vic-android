package com.example.vic.database.entities

import com.example.vic.database.enums.Role

data class Account(
    var id: Long,
    var firstName: String,
    var lastName: String,
    var email: String,
    var role: Role,
    var password: String,
    var isActive: Boolean,
    var department: String,
    var education: String,
)
