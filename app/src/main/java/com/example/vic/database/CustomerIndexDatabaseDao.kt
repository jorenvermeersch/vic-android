package com.example.vic.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vic.domain.entities.CustomerIndex

@Dao
interface CustomerIndexDatabaseDao {
    @Query("SELECT * FROM customer_index_table")
    fun getAll(): LiveData<List<DatabaseCustomerIndex>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg customerIndexes: DatabaseCustomerIndex)
}
