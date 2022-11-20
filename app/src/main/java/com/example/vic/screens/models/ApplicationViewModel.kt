package com.example.vic.screens.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.vic.database.MockVicDatabase
import com.example.vic.database.entities.Customer
import com.example.vic.database.entities.CustomerIndex
import com.example.vic.database.entities.VirtualMachine

class ApplicationViewModel : ViewModel() {

    private val database = MockVicDatabase()
    // TODO: Database mocks API currently.
    //  Add Room Database to store and fetch customers user adds using app.

    private val _searchQuery = MutableLiveData<String?>(null)

    // All customers.
    private val _customers = MutableLiveData<List<CustomerIndex>>(listOf())
    private val customers: LiveData<List<CustomerIndex>> get() = _customers

    val filteredCustomers: LiveData<List<CustomerIndex>> = Transformations.switchMap(_searchQuery) {
        filterCustomers(_searchQuery.value)
    }

    // Locally stored customers.
    private val _locallyStoredCustomers = MutableLiveData<List<CustomerIndex>>(listOf())
    val locallyStoredCustomers: LiveData<List<CustomerIndex>> get() = _locallyStoredCustomers
    // TODO: Fetch from Room Database.


    // Customer selected by user.
    private val _chosenCustomer = MutableLiveData<Customer>(null)
    val chosenCustomer: LiveData<Customer> get() = _chosenCustomer

    // Virtual machine selected by user.
    private val _chosenVirtualMachine = MutableLiveData<VirtualMachine>(null)
    val chosenVirtualMachine: LiveData<VirtualMachine> get() = _chosenVirtualMachine


    init {
        _customers.value = database.getCustomers().value
    }

    fun onCustomerSearch(query: String) {
        _searchQuery.value = query
    }

    fun resetCustomerSearch() {
        _searchQuery.value = null
    }

    fun onCustomerClicked(customerId: Long) {
        val customer = database.getCustomerById(customerId)
        _chosenCustomer.value = customer.value
    }

    fun onVirtualMachineClicked(machineId: Long) {
        val virtualMachine = database.getVirtualMachineById(machineId)
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