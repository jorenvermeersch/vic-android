package com.example.vic.domain.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ContactPerson(
    var id: Long,
    var firstName: String,
    var lastName: String,
    var email: String,
    var phoneNumber: String?,
)
