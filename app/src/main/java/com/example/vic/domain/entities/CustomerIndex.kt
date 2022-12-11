package com.example.vic.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

data class CustomerIndexData(
    var customers: List<CustomerIndex>,
    var totalAmount: Long
)

@Entity(tableName = "customer_index_table")
data class CustomerIndex(
    @PrimaryKey
    var id: Long,
    var name: String,
)
