package com.example.vic.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.vic.database.VicDatabase
import com.example.vic.database.asDomainModel
import com.example.vic.domain.entities.Customer
import com.example.vic.domain.entities.CustomerIndex
import com.example.vic.domain.entities.VirtualMachine
import com.example.vic.misc.Global
import com.example.vic.network.ApiCustomerContainer
import com.example.vic.network.ApiCustomerPostResponse
import com.example.vic.network.ApiCustomersContainer
import com.example.vic.network.ApiVirtualMachinesContainer
import com.example.vic.network.CustomerApi
import com.example.vic.network.VirtualMachineApi
import com.example.vic.network.asDatabaseModel
import com.example.vic.network.asDomainModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CustomerIndexRepository(private val database: VicDatabase, private val context: Context) {

    val customerIndexes: LiveData<List<CustomerIndex>> =
        Transformations.map(database.customerIndexDatabaseDao.getAll()) {
            it.asDomainModel()
        }

    suspend fun refreshCustomerIndexes() {

        if (Global.isOnline(context)) {
            withContext(Dispatchers.IO) {
                val results = CustomerApi.retrofitService.getCustomerIndexes().await()
                database.customerIndexDatabaseDao.insertAll(*results.asDatabaseModel())
            }
        }
    }

    suspend fun fetchVirtualMachines(): List<VirtualMachine>? {
        var results: Deferred<ApiVirtualMachinesContainer>? = null
        var vmlist: List<VirtualMachine>? = null
        if (Global.isOnline(context)) {
            withContext(Dispatchers.IO) {
                val results = VirtualMachineApi.retrofitService.getAllVirtualMachines().await()
                vmlist = results.virtualMachines.map { it.asDomainModel() }
            }
        }
        return vmlist
    }

    suspend fun fetchCustomers(): List<Customer>? {
        var results: Deferred<ApiCustomersContainer>? = null
        var customerlist: List<Customer>? = null
        if (Global.isOnline(context)) {
            withContext(Dispatchers.IO) {
                val results = CustomerApi.retrofitService.getAllCustomers().await()
                customerlist = results.customers.map { it.asDomainModel() }
            }
        }
        return customerlist
    }

    suspend fun createCustomer(customer: ApiCustomerContainer): Long? {
        var results: ApiCustomerPostResponse
        var id: Long? = null
        if (Global.isOnline(context)) {
            withContext(Dispatchers.IO) {
                results = CustomerApi.retrofitService.createCustomer(customer).await()
                id = results.customerId
            }
        }
        return id
    }
}
