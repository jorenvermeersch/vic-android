package com.example.vic.database.entities

import com.example.vic.database.enums.Status

data class VirtualMachineIndex(
    val id: Long,
    val name: String,
    val status: Status,
)