package com.example.vic.screens.models

import android.app.Application
import androidx.lifecycle.*
import com.example.vic.database.CustomerIndexDao
import com.example.vic.database.MockApi
import com.example.vic.database.entities.Customer
import com.example.vic.database.entities.CustomerIndex
import com.example.vic.database.entities.VirtualMachine

class ApplicationViewModel(val database: CustomerIndexDao, application: Application) :
    AndroidViewModel(application) {

    private val mockApi = MockApi()

    private val _searchQuery = MutableLiveData<String?>(null)

    // All customers.
    private val _customers = MutableLiveData<List<CustomerIndex>>(listOf())
    private val customers: LiveData<List<CustomerIndex>> get() = _customers

    val filteredCustomers: LiveData<List<CustomerIndex>> = Transformations.switchMap(_searchQuery) {
        filterCustomers(_searchQuery.value)
    }

    // Customer selected by user.
    private val _chosenCustomer = MutableLiveData<Customer?>(null)
    val chosenCustomer: LiveData<Customer?> get() = _chosenCustomer

    // Virtual machine selected by user.
    private val _chosenVirtualMachine = MutableLiveData<VirtualMachine>(null)
    val chosenVirtualMachine: LiveData<VirtualMachine> get() = _chosenVirtualMachine


    init {
        _customers.value = mockApi.getCustomers().value
    }

    fun onCustomerSearch(query: String) {
        _searchQuery.value = query
    }

    fun resetCustomerSearch() {
        _searchQuery.value = null
    }

    fun onCustomerClicked(customerId: Long) {
        val customer = mockApi.getCustomerById(customerId)
        _chosenCustomer.value = customer.value
    }

    fun onVirtualMachineClicked(machineId: Long) {
        val virtualMachine = mockApi.getVirtualMachineById(machineId)
        _chosenVirtualMachine.value = virtualMachine.value
    }

    private fun filterCustomers(query: String?): LiveData<List<CustomerIndex>> {
        if (query.isNullOrBlank()) {
            return customers
        }

        val resultContainer = MutableLiveData<List<CustomerIndex>>()
        val results = _customers.value?.filter { it -> it.name.contains(query) }
        resultContainer.value = results ?: listOf()

        return resultContainer
    }

}