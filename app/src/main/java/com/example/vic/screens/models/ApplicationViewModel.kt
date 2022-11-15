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
    private val _customers = MutableLiveData<List<CustomerIndex>>()
    val customers: LiveData<List<CustomerIndex>> get() = _customers

    // All virtual machines of chosen customer.
    private val _customerMachines = MutableLiveData<List<VirtualMachineIndex>>()
    val customerMachines: LiveData<List<VirtualMachineIndex>> get() = _customerMachines

    // Customer selected by user.
    private val _chosenCustomer = MutableLiveData<Customer>()
    val chosenCustomer: LiveData<Customer> get() = _chosenCustomer

    // Virtual machine selected by user.
    private val _chosenVirtualMachine = MutableLiveData<VirtualMachine>()
    val chosenVirtualMachine: LiveData<VirtualMachine> get() = _chosenVirtualMachine


    init {
        _customers.value = listOf()
        _customerMachines.value = listOf()
    }

    fun showCustomer(customerId: Long) {
        // TODO: Fetch customer with given id from database. Set as chosenCustomer.
        // TODO: Don't forget to fetch virtual machines of customer.
    }

    fun showVirtualMachine(machineId: Long) {
        // TODO: Fetch virtual machine with given id from database. Set as chosenVirtualMachine.
    }

    private fun populate() {
        // TODO: Generate some mock data for display testing.
    }
}