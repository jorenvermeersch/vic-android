package com.example.vic.domain.entities

import com.example.vic.domain.enums.Availability
import com.example.vic.domain.enums.BackupFrequency
import com.example.vic.domain.enums.Mode
import com.example.vic.domain.enums.Status
import com.example.vic.domain.enums.Template

data class VirtualMachine(
    var id: Long? = null,
    var name: String? = null,
    var specifications: Specifications? = null,
    var template: Template? = null,
    var mode: Mode? = null,
    var fqdn: String? = null,
    var availabilities: List<Availability>? = null,
    var backupFrequency: BackupFrequency? = null,
    var applicationDate: String? = null,
    var startDate: String? = null,
    var endDate: String? = null,
    var status: Status? = null,
    var reason: String? = null,
    var ports: List<Port>? = null,
    var host: Host? = null,
    var credentials: List<Credentials>? = null,
    var account: Account? = null,
    var requester: Customer? = null,
    var user: Customer? = null,
)
