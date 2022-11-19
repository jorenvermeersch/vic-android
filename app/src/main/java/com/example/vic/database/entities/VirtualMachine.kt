package com.example.vic.database.entities

import com.example.vic.database.enums.*
import java.util.*

data class VirtualMachine(
    val id: Long,
    val name: String,
    val specifications: Specifications,
    val template: Template,
    val mode: Mode,
    val fqdn: String,
    val availabilities: List<Availability>,
    val backupFrequency: BackupFrequency,
    val applicationDate: Date,
    val startDate: Date,
    val endDate: Date,
    val status: Status,
    val reason: String,
    val ports: List<Port>,
    val host: Host,
    val credentials: List<Credentials>,
    val account: Account?,
    var requester: Customer?,
    var user: Customer?,
) {
}


