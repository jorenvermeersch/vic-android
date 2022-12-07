package com.example.vic.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "customer_index_table")
data class CustomerIndex(
    @PrimaryKey
    @Json(name = "id")
    var id: Long,

    @ColumnInfo(name = "name")
    @Json(name = "name")
    var name: String,
)
