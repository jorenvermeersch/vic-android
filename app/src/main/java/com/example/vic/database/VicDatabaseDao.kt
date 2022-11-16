package com.example.vic.database


import androidx.lifecycle.LiveData
import com.example.vic.database.entities.Customer
import com.example.vic.database.entities.CustomerIndex
import com.example.vic.database.entities.VirtualMachine

interface VicDatabaseDao {
    // TODO: Will we store information locally. Probably not.

    fun insertCustomer(customer: Customer) : LiveData<Customer>

    fun getCustomers() : LiveData<List<CustomerIndex>>

    fun getCustomerById(customerId : Long) : LiveData<Customer>

    fun getVirtualMachineById() : LiveData<VirtualMachine>
}