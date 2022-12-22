package com.example.vic.network

import com.example.vic.domain.entities.ContactPerson
import com.example.vic.domain.entities.Customer
import com.example.vic.domain.entities.VirtualMachineIndex
import com.example.vic.domain.enums.CustomerType
import com.example.vic.domain.enums.Status
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostAnswer(
    @Json(name = "answer")
    var answer: String?
)

@JsonClass(generateAdapter = true)
data class ApiCustomerContainer(
    @Json(name = "customer")
    var customer: ApiCustomer?,
)

data class ApiCustomersContainer(
    @Json(name = "customers")
    var customers: List<ApiCustomer>,
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

@JsonClass(generateAdapter = true)
data class ApiContactPerson(
    @Json(name = "firstname") var firstName: String,
    @Json(name = "lastname") var lastName: String,
    @Json(name = "email") var email: String,
    @Json(name = "phonenumber") var phoneNumber: String?,
)

@JsonClass(generateAdapter = true)
data class ApiVm(
    @Json(name = "id") var id: Long = -1,
    @Json(name = "fqdn") var fqdn: String = "",
    @Json(name = "status") var status: Int = -1,
)

fun ApiCustomer.asDomainModel(): Customer {
    return Customer(
        id = id,
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
        virtualMachines = virtualMachines!!.map {
            VirtualMachineIndex(
                it.id, it.fqdn,
                when (it.status) {
                    0 -> Status.Requested
                    1 -> Status.InProgress
                    2 -> Status.ReadyToDeploy
                    3 -> Status.Deployed
                    else -> Status.Requested
                }
            )
        }
    )
}

fun ApiCustomerContainer.asDomainModel(): Customer {
    return customer.let {
        return Customer(
            id = 1,
            customerType = when (it!!.customerType) {
                0 -> CustomerType.Internal
                1 -> CustomerType.External
                else -> CustomerType.Unknown
            },
            contactPerson = ContactPerson(null, it.apiContactPerson!!.firstName, it.apiContactPerson!!.lastName, it.apiContactPerson!!.email, it.apiContactPerson!!.phoneNumber),
            backupContactPerson = ContactPerson(null, it.apiBackupContactPerson!!.firstName, it.apiBackupContactPerson!!.lastName, it.apiBackupContactPerson!!.email, it.apiBackupContactPerson!!.phoneNumber),
            institution = when (it!!.institution) {
                0 -> "Hogent"
                1 -> "Ehb"
                else -> "Niet gekend"
            },
            department = it.department,
            education = it.edu,
            type = it.companyType,
            companyName = it.companyName,
            virtualMachines = it.virtualMachines!!.map {
                VirtualMachineIndex(
                    it.id, it.fqdn,
                    when (it.status) {
                        0 -> Status.Requested
                        1 -> Status.InProgress
                        2 -> Status.ReadyToDeploy
                        3 -> Status.Deployed
                        else -> Status.Requested
                    }
                )
            }
        )
    }
}
