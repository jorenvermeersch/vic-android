package com.example.vic.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.vic.domain.entities.CustomerIndex
import com.squareup.moshi.Json

@Entity(tableName = "customer_index_table")
data class DatabaseCustomerIndex(
    @PrimaryKey
    @Json(name = "id")
    var id: Long,

    @ColumnInfo(name = "name")
    @Json(name = "name")
    var name: String,
)

fun List<DatabaseCustomerIndex>.asDomainModel(): List<CustomerIndex> {
    return map {
        CustomerIndex(
            id = it.id,
            name = it.name
        )
    }
}
