package com.example.vic.database.entities

import com.example.vic.database.enums.CustomerType

data class Customer(
    val id: Long,
    val customerType: CustomerType,
    val contactPerson: ContactPerson,
    val backupContactPerson: ContactPerson?,
    val institution: String?,
    val type: String?,
    val companyName: String?,
    val virtualMachines: List<VirtualMachineIndex>
) {

}