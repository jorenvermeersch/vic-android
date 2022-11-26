package com.example.vic.database.entities

import com.example.vic.database.enums.Status

data class VirtualMachineIndex(
    var id: Long,
    var name: String,
    var status: Status,
)