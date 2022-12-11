package com.example.vic.domain.entities

import com.example.vic.domain.enums.Status

data class VirtualMachineIndex(
    var id: Long,
    var name: String,
    var status: Status,
)
