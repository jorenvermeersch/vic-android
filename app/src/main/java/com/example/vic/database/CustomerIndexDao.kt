package com.example.vic.database


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.vic.database.entities.CustomerIndex

@Dao
interface CustomerIndexDao {
    @Query("SELECT * FROM customer_index_table")
    fun getAll() : LiveData<List<CustomerIndex>>

    @Insert
    fun insertAll(customerIndexes : List<CustomerIndex>)
}