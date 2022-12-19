package com.example.vic.screens.models

import android.app.Application
import androidx.lifecycle.*
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
//    private val realApi =

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


//    private val _customers = MutableLiveData<List<CustomerIndex>>()
//    val customers: LiveData<List<CustomerIndex>> get() = _customers
//
//    init {
//        refreshDataFromNetwork()
//    }
//
//    val customerIndexes = repository.customerIndexes
//
//    private fun refreshDataFromNetwork() = viewModelScope.launch {
//        val results =  CustomerApi.retrofitService.getCustomerIndexes().await()
//        _customers.postValue(results.asDomainModel())
//    }

//    // All customers.
//    private val _customers = MutableLiveData<List<CustomerIndex>>(listOf())
//    private val customers: LiveData<List<CustomerIndex>> get() = _customers
//
//    init {
//        getAllCustomers()
//    }
//
//    private fun getAllCustomers() {
//
//        coroutineScope.launch {
//            var getCustomerIndexDeferrerd = CustomerApi.retrofitService.getCustomerIndexes()
//            try {
//                var result = getCustomerIndexDeferrerd.await()
//                _response.value = "SUCCESS: ${result.apiCustomerIndexes.size} ITEMS WERE FOUND"
//                _customers.value = result.apiCustomerIndexes.map { CustomerIndex(id = it.id, name = it.name) }
//            } catch (t: Throwable) {
//                _response.value = "Could not fetch data"
//            }
//        }

//        CustomerApi.retrofitService.getCustomerIndexes().enqueue(object: Callback<List<CustomerIndex>> {
//            override fun onResponse(call: Call<List<CustomerIndex>>, response: Response<List<CustomerIndex>>) {
//                _response.value = "SUCCESS: ${response.body()?.size} ITEMS WERE FOUND"
//                _customers =  response.body()
//            }
//
//            override fun onFailure(call: Call<List<CustomerIndex>>, t: Throwable) {
//                _response.value = "rip"
//            }
//        })
//    }

    fun onCustomerSearch(query: String) {
        _searchQuery.value = query
    }

    fun resetCustomerSearch() {
        _searchQuery.value = null
    }

    fun onCustomerClicked(customerId: Long) {
        val customer = api.getCustomerById(customerId)
        _chosenCustomer.value = customer.value
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
//        val results = _customers.value?.filter { it -> it.name.contains(query) }
        val results = customers.value?.filter { it -> it.name.contains(query) }
        resultContainer.value = results ?: listOf()

        return resultContainer
    }
}
