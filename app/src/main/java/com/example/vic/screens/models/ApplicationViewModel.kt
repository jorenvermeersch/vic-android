package com.example.vic.screens.models

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.vic.database.CustomerIndexDatabaseDao
import com.example.vic.database.MockApi
import com.example.vic.database.VicDatabase
import com.example.vic.database.VicDatabase.Companion.getInstance
import com.example.vic.domain.entities.Customer
import com.example.vic.domain.entities.CustomerIndex
import com.example.vic.domain.entities.VirtualMachine
import com.example.vic.misc.GlobalMethods
import com.example.vic.network.ApiCustomerContainer
import com.example.vic.network.CustomerApi
import com.example.vic.network.VirtualMachineApi
import com.example.vic.network.asDomainModel
import com.example.vic.repository.CustomerIndexRepository
import com.example.vic.screens.customerlist.CustomerListFragmentDirections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

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
    private val _chosenCustomer = MutableLiveData<Customer?>()
    val chosenCustomer: LiveData<Customer?> get() = _chosenCustomer

    // Virtual machine selected by user.
    private val _chosenVirtualMachine = MutableLiveData<VirtualMachine?>(null)
    val chosenVirtualMachine: LiveData<VirtualMachine?> get() = _chosenVirtualMachine

    private val datab: VicDatabase = getInstance(application)
    private val repository = CustomerIndexRepository(datab, application)

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

    suspend fun findCustomer(customerId: Long?): Deferred<ApiCustomerContainer> {
        var result: ApiCustomerContainer? = null;
        var getCustomerDetails = CustomerApi.retrofitService.getCustomerById(customerId)

        coroutineScope.launch {
            try {
                result = getCustomerDetails.await()
                var customerDomainified = result!!.asDomainModel()
                _chosenCustomer.value = customerDomainified



            } catch (e: Exception) {
                Log.i("Error whot fetching the customer details: ", e.message.toString())
            }
        }

        Log.i("vms available", chosenCustomer.value!!.virtualMachines.toString())

        return getCustomerDetails
    }

//    fun onCustomerClicked(customerId: Long?): Deferred<Customer>? {
//        var newdata: Boolean = false;
//        coroutineScope.launch {
//            var result: ApiCustomerContainer? = null;
//            var getCustomerDetails = CustomerApi.retrofitService.getCustomerById(customerId)
//            try {
//                result = getCustomerDetails.await()
//                var customerDomainified = result!!.asDomainModel()
//                _chosenCustomer.value = customerDomainified
//                newdata = true
//            } catch (e: Exception) {
//                Log.i("Error whot fetching the customer details: ", e.message.toString())
//            }
//        }
//    }

    fun setChosenCustomer(customer: Customer?) {
        _chosenCustomer.value = customer
    }

    fun onVirtualMachineClicked(machineId: Long) {
        coroutineScope.launch {
            var getVirtualMachineDetails = VirtualMachineApi.retrofitService.getVirtualMachineById("5359d02c-aa72-4246-bb66-9756b2f42d78")
            Timber.d("resultvm: not yet ...")
            try {
                Timber.d("resultvm: not yet")
                var result = getVirtualMachineDetails.await()
                Timber.d("resultvm: " + result!!.virtualMachine!!.name)
                _chosenVirtualMachine.value = result.virtualMachine!!.asDomainModel()
            } catch (e: Exception) {
                _chosenVirtualMachine.value = null
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
