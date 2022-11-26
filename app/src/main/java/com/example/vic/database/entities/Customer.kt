package com.example.vic.database.entities

import com.example.vic.database.enums.CustomerType

data class Customer(
    var id: Long,
    var customerType: CustomerType,
    var contactPerson: ContactPerson,
    var backupContactPerson: ContactPerson?,
    var institution: String?, // Internal.
    var department : String?, // Internal.
    var education : String?, // Internal.
    var type: String?, // External.
    var companyName: String?, // External.
    var virtualMachines: List<VirtualMachineIndex>
)