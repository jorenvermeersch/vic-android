package com.example.vic.screens.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.vic.database.CustomerIndexDao
import com.example.vic.database.MockApi
import com.example.vic.database.entities.Customer
import com.example.vic.database.entities.CustomerIndex
import com.example.vic.database.entities.VirtualMachine
import com.example.vic.network.CustomerApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ApplicationViewModel(val database: CustomerIndexDao, application: Application) :
    AndroidViewModel(application) {

    private val api = MockApi()
//    private val realApi =

    private val _searchQuery = MutableLiveData<String?>(null)

    // All customers.
    private val _customers = MutableLiveData<List<CustomerIndex>>(listOf())
    private val customers: LiveData<List<CustomerIndex>> get() = _customers

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

    init {
        getAllCustomers()
    }

    private fun getAllCustomers() {

        coroutineScope.launch {
            var getCustomerIndexDeferrerd = CustomerApi.retrofitService.getCustomerIndexes()
            try {
                var result = getCustomerIndexDeferrerd.await()
                _response.value = "SUCCESS: ${result.totalAmount} ITEMS WERE FOUND"
                _customers.value = result.customers
            } catch (t: Throwable) {
                _response.value = "Could not fetch data"
            }
        }

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
    }

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
        val results = _customers.value?.filter { it -> it.name.contains(query) }
        resultContainer.value = results ?: listOf()

        return resultContainer
    }
}
