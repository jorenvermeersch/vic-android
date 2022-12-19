package com.example.vic.domain.entities

data class CustomerIndexData(
    var customers: List<CustomerIndex>,
    var totalAmount: Long
)

data class CustomerIndex(
    var id: Long,
    var name: String,
)
