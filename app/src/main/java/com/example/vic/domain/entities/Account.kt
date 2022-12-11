package com.example.vic.domain.entities

import com.example.vic.domain.enums.Role

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
