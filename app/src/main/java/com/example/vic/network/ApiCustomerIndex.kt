package com.example.vic.network

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.example.vic.database.DatabaseCustomerIndex
import com.example.vic.domain.entities.CustomerIndex
import com.squareup.moshi.Json

data class ApiCustomerIndexContainer (
    @Json(name = "customers")
    val apiCustomerIndexes: List<ApiCustomerIndex>
)


data class ApiCustomerIndex (
    @Json(name = "id")
    var id: Long,

    @Json(name = "name")
    var name: String,
)


/*
* Convert network results into Domain jokes
* */
fun ApiCustomerIndexContainer.asDomainModel(): List<CustomerIndex>{
    return apiCustomerIndexes.map{
        CustomerIndex(id = it.id, name = it.name)
    }
}

/*
* Convert network result into Database jokes
*
* returns an array that can be used in the insert call as vararg
* */
fun ApiCustomerIndexContainer.asDatabaseModel(): Array<DatabaseCustomerIndex>{
    return apiCustomerIndexes.map{
        DatabaseCustomerIndex(id = it.id, name = it.name)
    }.toTypedArray()
}

/*
* Convert a single api joke to a database joke
* */
fun ApiCustomerIndex.asDatabaseJoke(): DatabaseCustomerIndex{
    return DatabaseCustomerIndex(id = id, name = name)
}