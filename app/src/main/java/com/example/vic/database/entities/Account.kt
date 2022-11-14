package com.example.vic.database.entities

import com.example.vic.database.enums.Role

data class Account(
    val id : Long,
    val firstName : String,
    val lastName : String,
    val email : String,
    val role : Role,
    val password : String,
    val isActive : Boolean,
    val department : String,
    val education : String,
    ) {

}