package com.example.vic.repository

import android.net.Network
import com.example.vic.database.VicDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CustomerIndexRepository(private val database: VicDatabase) {
    suspend fun refreshCustomerIndexes() {
        withContext(Dispatchers.IO) {

        }
    }
}
