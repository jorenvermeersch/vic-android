package com.example.vic.screens.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vic.database.entities.Customer
import com.example.vic.database.entities.CustomerIndex
import com.example.vic.database.entities.VirtualMachine
import com.example.vic.database.entities.VirtualMachineIndex

class ApplicationViewModel : ViewModel() {

    // All customers.
    private val _customers = MutableLiveData<List<CustomerIndex>>(listOf())
    val customers: LiveData<List<CustomerIndex>> get() = _customers

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
}