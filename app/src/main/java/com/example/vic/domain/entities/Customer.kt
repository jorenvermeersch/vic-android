package com.example.vic.domain.entities

import com.example.vic.domain.enums.CustomerType
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Customer(
    var id: Long,
    var customerType: CustomerType,
    var contactPerson: ContactPerson,
    var backupContactPerson: ContactPerson?,
    var institution: String?, // Internal.
    var department: String?, // Internal.
    var education: String?, // Internal.
    var type: String?, // External.
    var companyName: String?, // External.
    var virtualMachines: List<VirtualMachineIndex>
)
