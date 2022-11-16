package com.example.vic.database.entities

data class ContactPerson(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String?,
) {

}