package com.example.vic.database

import androidx.room.Dao
import androidx.room.Insert
import com.example.vic.database.entities.Customer

@Dao
interface VicDatabaseDao {
    // TODO: Will we store information locally. Probably not.
    @Insert
    fun insertCustomer(customer: Customer)

//    fun getCustomers() : LiveData<List<CustomerIndex>>
//
//    fun getCustomerById() : LiveData<Customer>
//
//    fun getVirtualMachineById() : LiveData<VirtualMachine>
}