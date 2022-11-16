package com.example.vic.screens.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.vic.database.MockVicDatabase
import com.example.vic.database.entities.Customer
import com.example.vic.database.entities.CustomerIndex
import com.example.vic.database.entities.VirtualMachine
import com.example.vic.database.entities.VirtualMachineIndex

class ApplicationViewModel : ViewModel() {

    private val database = MockVicDatabase()

    private val _searchQuery = MutableLiveData<String?>(null)

    // All customers.
    private val _customers = MutableLiveData<List<CustomerIndex>>(listOf())
    val customers: LiveData<List<CustomerIndex>> get() = _customers

    val filteredCustomers: LiveData<List<CustomerIndex>> = Transformations.switchMap(_searchQuery) {
        filterCustomers(_searchQuery.value)
    }

    // All virtual machines of chosen customer.
    private val _customerVirtualMachines = MutableLiveData<List<VirtualMachineIndex>>(listOf())
    val customerVirtualMachines: LiveData<List<VirtualMachineIndex>> get() = _customerVirtualMachines

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

    fun onVirtualMachineSearch(query: String) {

    }

    fun onCustomerClicked(customerId: Long) {
        // TODO: Fetch customer from database or API.
        val result = database.getCustomerById(customerId)
        _chosenCustomer.value = result.value
    }

    fun onVirtualMachineClicked(machineId: Long) {
        // TODO: Fetch virtual machine from database or API.
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

    fun resetCustomerSearch() {
        _searchQuery.value = null
    }
}