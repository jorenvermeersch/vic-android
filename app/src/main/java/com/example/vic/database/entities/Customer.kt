package com.example.vic.database.entities

import com.example.vic.database.enums.CustomerType

data class Customer(
    val id: Long,
    val customerType: CustomerType,
    val contactPerson: ContactPerson,
    val backupContactPerson: ContactPerson?,
    val institution: String?, // Internal.
    val department : String?, // Internal.
    val education : String?, // Internal.
    val type: String?, // External.
    val companyName: String?, // External.
    val virtualMachines: List<VirtualMachineIndex>
) {

}