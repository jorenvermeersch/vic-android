package com.example.vic.network

import com.example.vic.domain.entities.ContactPerson
import com.example.vic.domain.entities.Customer
import com.example.vic.domain.entities.VirtualMachine
import com.example.vic.domain.entities.VirtualMachineIndex
import com.example.vic.domain.enums.CustomerType
import com.example.vic.domain.enums.Status
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

//
// {
//     "customer": {
//     "id": 1,
//     "institution": null,
//     "department": null,
//     "education": null,
//      "customerType": 1,
//     "companyType": "Voka",
//     "companyName": "Jacobs, Bakker and Broek",
//     "contactPerson": {
//     "firstname": "Max",
//     "lastname": "Vliet",
//     "email": "Rick_Dijk46@gmail.com",
//     "phonenumber": "06 9793 0815"
// },
//     "backupContactPerson": {
//     "firstname": "Stijn",
//     "lastname": "Jansen",
//     "email": "Luuk.Berg@hotmail.com",
//     "phonenumber": "9379393915"
// },
//     "virtualMachines": []
// }
// }

@JsonClass(generateAdapter = true)
data class ApiCustomerContainer(
    @Json(name = "customer")
    var customer: ApiCustomer?,
)

@JsonClass(generateAdapter = true)
data class ApiCustomer(
    @Json(name = "id")
    var id: Long?,
    @Json(name = "companyName")
    var companyName: String?,
    @Json(name = "companyType")
    var companyType: String?,
    @Json(name = "institution")
    var institution: Int?,
    @Json(name = "department")
    var department: String?,
    @Json(name = "education")
    var edu: String?,
    @Json(name = "customerType")
    var customerType: Int?,
    @Json(name = "contactPerson")
    var apiContactPerson: ApiContactPerson?,
    @Json(name = "backupContactPerson")
    var apiBackupContactPerson: ApiContactPerson?,
    @Json(name = "virtualMachines")
    var virtualMachines: List<ApiVm>?
)

data class ApiContactPerson(
    @Json(name = "firstname") var firstName: String,
    @Json(name = "lastname") var lastName: String,
    @Json(name = "email") var email: String,
    @Json(name = "phonenumber") var phoneNumber: String?,
)

data class ApiVm(
    @Json(name = "id") var id: Long = -1,
    @Json(name = "fqdn") var fqdn: String = "",
    @Json(name = "status") var status: Int = -1,
)

//Requested,
//InProgress,
//ReadyToDeploy,
//Deployed

fun ApiCustomer.asDomainModel(): Customer {

    return Customer(
        id = 1,
        customerType = when (customerType) {
            0 -> CustomerType.Internal
            1 -> CustomerType.External
            else -> CustomerType.Unknown
        },
        contactPerson = ContactPerson(null, apiContactPerson!!.firstName, apiContactPerson!!.lastName, apiContactPerson!!.email, apiContactPerson!!.phoneNumber),
        backupContactPerson = ContactPerson(null, apiBackupContactPerson!!.firstName, apiBackupContactPerson!!.lastName, apiBackupContactPerson!!.email, apiBackupContactPerson!!.phoneNumber),
        institution = when (institution) {
            0 -> "Hogent"
            1 -> "Ehb"
            else -> "Niet gekend"
        },
        department = department,
        education = edu,
        type = companyType,
        companyName = companyName,
        virtualMachines = virtualMachines!!.map { VirtualMachineIndex( it.id, it.fqdn, when (it.status) {
            0 -> Status.Requested
            1 -> Status.InProgress
            2 -> Status.ReadyToDeploy
            3 -> Status.Deployed
            else -> Status.Requested
        }) }
    )
}

//    var id: Long,
//    var customerType: CustomerType,
//    var contactPerson: ContactPerson,
//    var backupContactPerson: ContactPerson?,
//    var institution: String?, // Internal.
//    var department: String?, // Internal.
//    var education: String?, // Internal.
//    var type: String?, // External.
//    var companyName: String?, // External.
//    var virtualMachines: List<VirtualMachineIndex>
