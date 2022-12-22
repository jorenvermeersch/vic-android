package com.example.vic.network

import com.example.vic.database.DatabaseCustomerIndex
import com.example.vic.domain.entities.CustomerIndex
import com.squareup.moshi.Json

data class ApiCustomerIndexContainer(
    @Json(name = "customers")
    val apiCustomerIndexes: List<ApiCustomerIndex>
)

data class ApiCustomerPostResponse(
    @Json(name = "customerId")
    var customerId: Long?
)

data class ApiCustomerIndex(
    @Json(name = "id")
    var id: Long,

    @Json(name = "name")
    var name: String,
)

fun ApiCustomerIndexContainer.asDomainModel(): List<CustomerIndex> {
    return apiCustomerIndexes.map {
        CustomerIndex(id = it.id, name = it.name)
    }
}

fun ApiCustomerIndexContainer.asDatabaseModel(): Array<DatabaseCustomerIndex> {
    return apiCustomerIndexes.map {
        DatabaseCustomerIndex(id = it.id, name = it.name)
    }.toTypedArray()
}

fun ApiCustomerIndex.asDatabaseJoke(): DatabaseCustomerIndex {
    return DatabaseCustomerIndex(id = id, name = name)
}
