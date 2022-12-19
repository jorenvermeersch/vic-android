package com.example.vic.screens.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.vic.database.CustomerIndexDatabaseDao
import com.example.vic.database.MockApi
import com.example.vic.database.VicDatabase
import com.example.vic.database.VicDatabase.Companion.getInstance
import com.example.vic.domain.entities.Customer
import com.example.vic.domain.entities.CustomerIndex
import com.example.vic.domain.entities.VirtualMachine
import com.example.vic.network.CustomerApi
import com.example.vic.network.asDomainModel
import com.example.vic.repository.CustomerIndexRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ApplicationViewModel(val database: CustomerIndexDatabaseDao, application: Application) :
    AndroidViewModel(application) {

    private val api = MockApi()
    // private val realApi =
    private val _searchQuery = MutableLiveData<String?>(null)

    private val _response = MutableLiveData<String>("default value")
    public val response: LiveData<String> get() = _response

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val filteredCustomers: LiveData<List<CustomerIndex>> = Transformations.switchMap(_searchQuery) {
        filterCustomers(_searchQuery.value)
    }

    // Customer selected by user.
    private val _chosenCustomer = MutableLiveData<Customer?>(null)
    val chosenCustomer: LiveData<Customer?> get() = _chosenCustomer

    // Virtual machine selected by user.
    private val _chosenVirtualMachine = MutableLiveData<VirtualMachine>(null)
    val chosenVirtualMachine: LiveData<VirtualMachine> get() = _chosenVirtualMachine

    private val datab: VicDatabase = getInstance(application)
    private val repository = CustomerIndexRepository(datab)

    init {
        viewModelScope.launch {
            repository.refreshCustomerIndexes()
        }
    }

    val customers = repository.customerIndexes

    fun onCustomerSearch(query: String) {
        _searchQuery.value = query
    }

    fun resetCustomerSearch() {
        _searchQuery.value = null
    }

    fun onCustomerClicked(customerId: Long) {
        coroutineScope.launch {
            var getCustomerDetails = CustomerApi.retrofitService.getCustomerById("5533f0b6-d769-4b99-a88b-4b38a1dc4651")
            try {
                var result = getCustomerDetails.await()
                _chosenCustomer.value = result.customer!!.asDomainModel()
            } catch (e: Exception) {
                _chosenCustomer.value = null
            }
        }
    }

    fun onVirtualMachineClicked(machineId: Long) {
        val virtualMachine = api.getVirtualMachineById(machineId)
        _chosenVirtualMachine.value = virtualMachine.value
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
