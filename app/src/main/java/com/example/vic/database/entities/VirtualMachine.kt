package com.example.vic.database.entities

import com.example.vic.database.enums.Availability
import com.example.vic.database.enums.BackupFrequency
import com.example.vic.database.enums.Mode
import com.example.vic.database.enums.Status
import com.example.vic.database.enums.Template
import java.util.Date

data class VirtualMachine(
    var id: Long,
    var name: String,
    var specifications: Specifications,
    var template: Template,
    var mode: Mode,
    var fqdn: String,
    var availabilities: List<Availability>,
    var backupFrequency: BackupFrequency,
    var applicationDate: Date,
    var startDate: Date,
    var endDate: Date,
    var status: Status,
    var reason: String,
    var ports: List<Port>,
    var host: Host,
    var credentials: List<Credentials>,
    var account: Account?,
    var requester: Customer?,
    var user: Customer?,
)
