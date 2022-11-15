package com.example.vic.screens.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.vic.database.entities.Customer
import com.example.vic.database.entities.CustomerIndex
import com.example.vic.database.entities.VirtualMachine
import com.example.vic.database.entities.VirtualMachineIndex

class ApplicationViewModel : ViewModel() {

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
        populateCustomers() // Mock data.
    }

    fun onCustomerSearch(query: String) {
        _searchQuery.value = query
    }

    fun onVirtualMachineSearch(query: String) {

    }

    fun onCustomerClicked(customerId: Long) {
        // TODO: Fetch customer with given id from database. Set as chosenCustomer.
        // TODO: Don't forget to fetch virtual machines of customer.
    }

    fun onVirtualMachineClicked(machineId: Long) {
        // TODO: Fetch virtual machine with given id from database. Set as chosenVirtualMachine.
    }

    private fun populateCustomers() {
        val mockCustomers = mutableListOf<CustomerIndex>()

        for (customerId in 1..10) {
            val c = CustomerIndex(customerId.toLong(), "customer-${customerId}", customerId in 2..6)
            mockCustomers.add(c)
        }

        _customers.value = mockCustomers
    }

    private fun filterCustomers(query: String?): LiveData<List<CustomerIndex>> {
        if (query.isNullOrBlank()) {
            return customers
        }

        val container = MutableLiveData<List<CustomerIndex>>()
        val values = _customers.value?.filter { it -> it.name.contains(query) }
        container.value = values ?: listOf()

        return container
    }

    fun resetCustomerSearch() {
        _searchQuery.value = null
    }
}