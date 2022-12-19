package com.example.vic.network

import com.example.vic.domain.entities.Account
import com.example.vic.domain.entities.Credentials
import com.example.vic.domain.entities.Customer
import com.example.vic.domain.entities.Host
import com.example.vic.domain.entities.Port
import com.example.vic.domain.entities.Specifications
import com.example.vic.domain.entities.VirtualMachine
import com.example.vic.domain.enums.Availability
import com.example.vic.domain.enums.BackupFrequency
import com.example.vic.domain.enums.CustomerType
import com.example.vic.domain.enums.Mode
import com.example.vic.domain.enums.Role
import com.example.vic.domain.enums.Status
import com.example.vic.domain.enums.Template
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class ApiVirtualMachineContainer(
    @Json(name = "virtualMachine")
    var virtualMachine: ApiVirtualMachine
)

@JsonClass(generateAdapter = true)
data class ApiVirtualMachine(
    @Json(name = "name")
    var name: String?,
//    @Json(name = "template")
//    var template: Int?,
//    @Json(name = "mode")
//    var mode: Int?,
//    @Json(name = "availabilities")
//    var availabilities: List<String>?,
//    @Json(name = "backupFrequenty")
//    var backupFrequency: Int?,
//    @Json(name = "applicationDate")
//    var applicationDate: Date?,
//    @Json(name = "timeSpan")
//    var timespan: ApiTimeSpan?,
//    @Json(name = "reason")
//    var reason: String?,
//    @Json(name = "ports")
//    var ports: List<ApiPort>?,
//    @Json(name = "specification")
//    var specification: ApiSpecifications?,
//    @Json(name = "host")
//    var host: ApiHost?,
//    @Json(name = "credentials")
//    var credentials: List<ApiCredential>?,
//    @Json(name = "account")
//    var account: ApiAccount?,
//    @Json(name = "requester")
//    var requester: ApiUser?,
//    @Json(name = "user")
//    var user: ApiUser?,
//    @Json(name = "hasVpnConnection")
//    var hasVpnConnection: Boolean?,
    @Json(name = "id")
    var id: Long?,
//    @Json(name = "fqdn")
//    var fqdn: String?,
//    @Json(name = "status")
//    var status: Int?
)

@JsonClass(generateAdapter = true)
data class ApiTimeSpan(
    @Json(name = "startDate")
    var start: Date?,
    @Json(name = "endDate")
    var end: Date?
)

@JsonClass(generateAdapter = true)
data class ApiPort(
    @Json(name = "number")
    var number: Int?,
    @Json(name = "service")
    var service: String?
)

@JsonClass(generateAdapter = true)
data class ApiSpecifications(
    @Json(name = "virtualProcessors")
    var virtualProcessors: Int?,
    @Json(name = "memory")
    var memory: Int?,
    @Json(name = "storage")
    var storage: Int?
)

@JsonClass(generateAdapter = true)
data class ApiHost(
    @Json(name = "id")
    var id: Long?,
    @Json(name = "name")
    var name: String?
)

@JsonClass(generateAdapter = true)
data class ApiCredential(
    @Json(name = "username")
    var username: String?,
    @Json(name = "role")
    var role: String?,
    @Json(name = "passwordHash")
    var passwordHash: String?
)

@JsonClass(generateAdapter = true)
data class ApiAccount(
    @Json(name = "id")
    var id: Long?,
    @Json(name = "firstname")
    var firstname: String?,
    @Json(name = "lastname")
    var lastname: String?,
    @Json(name = "email")
    var email: String?,
    @Json(name = "role")
    var role: Int?,
    @Json(name = "isActive")
    var isActive: Boolean?
)

@JsonClass(generateAdapter = true)
data class ApiUser(
    @Json(name = "id")
    var id: Long?,
    @Json(name = "name")
    var name: String?,
    @Json(name = "customerType")
    var customerType: Int?,
    @Json(name = "email")
    var email: String?,
)

fun ApiVirtualMachine.asDomainModel(): VirtualMachine {

    return VirtualMachine(
        id = id,
        name = name,
//        specifications = Specifications(
//            processors = specification!!.virtualProcessors,
//            memory = specification!!.memory,
//            storage = specification!!.storage,
//        ),
//        template = when (template) {
//            0 -> Template.NoTemplate
//            1 -> Template.AI
//            2 -> Template.WebServer
//            else -> null
//        },
//        mode = when (mode) {
//            0 -> Mode.IAAS
//            1 -> Mode.PAAS
//            2 -> Mode.SAAS
//            else -> null
//        },
//        fqdn = fqdn,
//        availabilities = availabilities!!.map { Availability.valueOf(it) },
//        backupFrequency = when (backupFrequency) {
//            0 -> BackupFrequency.NoBackup
//            1 -> BackupFrequency.Daily
//            2 -> BackupFrequency.Weekly
//            3 -> BackupFrequency.Monthly
//            else -> BackupFrequency.NoBackup
//        },
//        applicationDate = applicationDate,
//        startDate = timespan!!.start,
//        endDate = timespan!!.end,
//        status = when (status) {
//            0 -> Status.Requested
//            1 -> Status.InProgress
//            2 -> Status.ReadyToDeploy
//            3 -> Status.Deployed
//            else -> null
//        },
//        reason = reason,
//        ports = ports!!.map {
//            Port(
//                number = it.number,
//                service = it.service
//            )
//        },
//        host = Host(
//            id = host!!.id,
//            name = host!!.name,
//            specifications = null
//        ),
//        credentials = credentials!!.map {
//            Credentials(
//                username = it.username,
//                passwordHash = it.passwordHash,
//                role = it.role
//            )
//        },
//        account = Account(
//            id = account!!.id,
//            firstName = account!!.firstname,
//            lastName = account!!.lastname,
//            email = account!!.email,
//            role = when (status) {
//                0 -> Role.Observer
//                1 -> Role.Admin
//                2 -> Role.Master
//                else -> null
//            },
//            password = null,
//            isActive = account!!.isActive,
//            department = null,
//            education = null
//        ),
//        requester = Customer(
//            id = requester!!.id,
//            customerType = when (requester!!.customerType) {
//                0 -> CustomerType.Internal
//                0 -> CustomerType.External
//                else -> CustomerType.Unknown
//            },
//            contactPerson = null,
//            backupContactPerson = null,
//            institution = null,
//            department = null,
//            education = null,
//            type = null,
//            companyName = requester!!.name,
//            virtualMachines = null
//            // TODO: add email to customer
//        ),
//        user = Customer(
//            id = user!!.id,
//            customerType = when (user!!.customerType) {
//                0 -> CustomerType.Internal
//                0 -> CustomerType.External
//                else -> CustomerType.Unknown
//            },
//            contactPerson = null,
//            backupContactPerson = null,
//            institution = null,
//            department = null,
//            education = null,
//            type = null,
//            companyName = user!!.name,
//            virtualMachines = null
//            // TODO: add email to customer
//        ),
    )
}
