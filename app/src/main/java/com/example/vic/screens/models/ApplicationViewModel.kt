package com.example.vic.screens.models

import android.app.Application
import android.util.Log
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
import com.example.vic.misc.GlobalMethods
import com.example.vic.network.ApiCustomerContainer
import com.example.vic.network.ApiVirtualMachineContainer
import com.example.vic.network.CustomerApi
import com.example.vic.network.VirtualMachineApi
import com.example.vic.network.asDomainModel
import com.example.vic.repository.CustomerIndexRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
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
    private val _allcustomers = MutableLiveData<List<Customer>>(null)
    val allcustomers: LiveData<List<Customer>> get() = _allcustomers

    private val _chosenCustomer = MutableLiveData<Customer?>()
    val chosenCustomer: LiveData<Customer?> get() = _chosenCustomer

    // Virtual machine selected by user.
    private val _allvirtualMachines = MutableLiveData<List<VirtualMachine>>(null)
    val allvirtualMachines: LiveData<List<VirtualMachine>> get() = _allvirtualMachines

    private val _chosenVirtualMachine = MutableLiveData<VirtualMachine?>(null)
    val chosenVirtualMachine: LiveData<VirtualMachine?> get() = _chosenVirtualMachine

    private val datab: VicDatabase = getInstance(application)
    private val repository = CustomerIndexRepository(datab, application)

    init {
        viewModelScope.launch {
            repository.refreshCustomerIndexes()

            if(GlobalMethods.isOnline(getApplication<Application>().applicationContext)) {
                _allvirtualMachines.value = repository.fetchVirtualMachines()
                _allcustomers.value = repository.fetchCustomers()

                Log.i("retrieved data vms output: ", allvirtualMachines.value!!.get(0).toString())
                Log.i("retrieved data customers output: ", allcustomers.value!!.get(5).toString())
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

//    suspend fun findCustomer(customerId: Long?): Deferred<ApiCustomerContainer> {
//
////        Log.i("vms output: ", virtualMachines.value!!.toString())
//
//        var result: ApiCustomerContainer? = null
//        var getCustomerDetails = CustomerApi.retrofitService.getCustomerById(customerId)
//
//        coroutineScope.launch {
//            try {
//                result = getCustomerDetails.await()
//                var customerDomainified = result!!.asDomainModel()
//                _chosenCustomer.value = customerDomainified
//            } catch (e: Exception) {
//                Log.i("Error whot fetching the customer details: ", e.message.toString())
//            }
//        }
//
//        Log.i("vms available", chosenCustomer.value!!.virtualMachines.toString())
//
//        return getCustomerDetails
//    }

    fun findCustomer(customerId: Long?) {
//        _chosenCustomer.value = allcustomers.value!!.get(customerId!!.toInt())
        if(allcustomers.value != null){
            _chosenCustomer.value = allcustomers.value!!.find { it.id == customerId }
        }
    }

    fun findVirtualMachine(machineId: Long?) {
        if(allvirtualMachines.value != null){
            _chosenVirtualMachine.value = allvirtualMachines.value!!.find { it.id == machineId }
        }
    }

    fun setChosenCustomer(customer: Customer?) {
        _chosenCustomer.value = customer
    }

//    suspend fun findVirtualMachine(machineId: Long?): Deferred<ApiVirtualMachineContainer> {
//        var result: ApiVirtualMachineContainer? = null
//        var getVirtualMachine = VirtualMachineApi.retrofitService.getVirtualMachineById(machineId)
//
//        coroutineScope.launch {
//            try {
//                result = getVirtualMachine.await()
//                var machineDomainified = result!!.virtualMachine!!.asDomainModel()
//                _chosenVirtualMachine.value = machineDomainified
//            } catch (e: Exception) {
//                Log.i("Error whot fetching the customer details: ", e.message.toString())
//            }
//        }
//        Log.i("vms available", chosenCustomer.value!!.virtualMachines.toString())
//        return getVirtualMachine
//    }

//    fun onVirtualMachineClicked(machineId: Long) {
//        coroutineScope.launch {
//            var getVirtualMachineDetails = VirtualMachineApi.retrofitService.getVirtualMachineById(machineId)
//            try {
//                var result = getVirtualMachineDetails.await()
//                _chosenVirtualMachine.value = result.virtualMachine!!.asDomainModel()
//            } catch (e: Exception) {
//                _chosenVirtualMachine.value = null
//            }
//        }
//        Timber.d("resultvm: " + chosenVirtualMachine.value.toString())
//    }

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
