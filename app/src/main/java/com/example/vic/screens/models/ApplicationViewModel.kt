package com.example.vic.screens.models

import android.app.Application
import android.util.Log
import android.widget.Toast
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
import com.example.vic.misc.Global
import com.example.vic.network.ApiCustomer
import com.example.vic.network.CustomerApi
import com.example.vic.network.PostAnswer
import com.example.vic.repository.CustomerIndexRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

            if (Global.isOnline(getApplication<Application>().applicationContext)) {
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

    fun findCustomer(customerId: Long?) {
//        _chosenCustomer.value = allcustomers.value!!.get(customerId!!.toInt())
        if (allcustomers.value != null) {
            _chosenCustomer.value = allcustomers.value!!.find { it.id == customerId }
        }
    }

    fun findVirtualMachine(machineId: Long?) {
        if (allvirtualMachines.value != null) {
            _chosenVirtualMachine.value = allvirtualMachines.value!!.find { it.id == machineId }
        }
    }

    fun createCustomer(customer: ApiCustomer) {
        viewModelScope.launch {
            if (Global.isOnline(getApplication<Application>().applicationContext)) {
//                var answer: PostAnswer? = repository.createCustomer(customer)
                Log.i("createcustomer", "yeshereher")

                val call = CustomerApi.retrofitService.createCustomer(customer)
                call.enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        if (response.isSuccessful) {
                            val responseString = response.body()
                            Log.i("createcustomer", responseString.toString())
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        // network failure
                        Log.i("createcustomer", "failed")
                    }
                })



                repository.refreshCustomerIndexes()
            } else {
                val toast = Toast.makeText(getApplication(), "Error: customer is niet toegevoegd", Toast.LENGTH_LONG)
                toast.show()
            }
        }
    }

    fun setChosenCustomer(customer: Customer?) {
        _chosenCustomer.value = customer
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
