package com.example.vic.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customer_index_table")
data class CustomerIndex(
    @PrimaryKey
    var id: Long,

    @ColumnInfo(name = "name")
    var name: String,
)
