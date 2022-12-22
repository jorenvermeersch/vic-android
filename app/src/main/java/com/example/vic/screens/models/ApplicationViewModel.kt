package com.example.vic.screens.models

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.vic.database.CustomerIndexDatabaseDao
import com.example.vic.database.VicDatabase
import com.example.vic.database.VicDatabase.Companion.getInstance
import com.example.vic.domain.entities.Customer
import com.example.vic.domain.entities.CustomerIndex
import com.example.vic.domain.entities.VirtualMachine
import com.example.vic.misc.Global
import com.example.vic.network.ApiCustomerContainer
import com.example.vic.repository.CustomerIndexRepository
import kotlinx.coroutines.launch

class ApplicationViewModel(val database: CustomerIndexDatabaseDao, application: Application) :
    AndroidViewModel(application) {

    private val _searchQuery = MutableLiveData<String?>(null)

    val filteredCustomers: LiveData<List<CustomerIndex>> = Transformations.switchMap(_searchQuery) {
        filterCustomers(_searchQuery.value)
    }

    // All customers.
    private val _allCustomers = MutableLiveData<List<Customer>>(null)
    val allCustomers: LiveData<List<Customer>> get() = _allCustomers

    // Customer selected by user.
    private val _chosenCustomer = MutableLiveData<Customer?>()
    val chosenCustomer: LiveData<Customer?> get() = _chosenCustomer

    // All virtual machines.
    private val _allVirtualMachines = MutableLiveData<List<VirtualMachine>>(null)
    val allVirtualMachines: LiveData<List<VirtualMachine>> get() = _allVirtualMachines

    // Virtual machine selected by user.
    private val _chosenVirtualMachine = MutableLiveData<VirtualMachine?>(null)
    val chosenVirtualMachine: LiveData<VirtualMachine?> get() = _chosenVirtualMachine

    private val vicDatabase: VicDatabase = getInstance(application)
    private val repository = CustomerIndexRepository(vicDatabase, application)

    init {
        viewModelScope.launch {
            repository.refreshCustomerIndexes()

            if (Global.isOnline(getApplication<Application>().applicationContext)) {
                _allVirtualMachines.value = repository.fetchVirtualMachines()
                _allCustomers.value = repository.fetchCustomers()
            }
        }
    }

    val customers = repository.customerIndexes

    fun onCustomerSearch(query: String) {
        _searchQuery.value = query
    }

    fun resetCustomerSearch() {
        _searchQuery.value = null
    }

    fun findCustomer(customerId: Long?) {
        if (allCustomers.value != null) {
            _chosenCustomer.value = allCustomers.value!!.find { it.id == customerId }
        }
    }

    fun findVirtualMachine(machineId: Long?) {
        if (allVirtualMachines.value != null) {
            _chosenVirtualMachine.value = allVirtualMachines.value!!.find { it.id == machineId }
        }
    }

    fun createCustomer(customer: ApiCustomerContainer) {
        viewModelScope.launch {

            if (Global.isOnline(getApplication<Application>().applicationContext)) {
                repository.createCustomer(customer)
                repository.refreshCustomerIndexes()
                _allVirtualMachines.value = repository.fetchVirtualMachines()
                _allCustomers.value = repository.fetchCustomers()
            } else {
                val toast = Toast.makeText(getApplication(), "Error: Klant is niet toegevoegd", Toast.LENGTH_LONG)
                toast.show()
            }
        }
    }

    private fun filterCustomers(query: String?): LiveData<List<CustomerIndex>> {
        if (query.isNullOrBlank()) {
            return customers
        }

        val resultContainer = MutableLiveData<List<CustomerIndex>>()
        val results = customers.value?.filter { it -> it.name.contains(query) }
        resultContainer.value = results ?: listOf()

        return resultContainer
    }
}
