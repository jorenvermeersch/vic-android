package com.example.vic.database.entities

data class Customer(
    val id: Long,
    val contactPerson: ContactPerson,
    val backupContactPerson: ContactPerson
) {

}