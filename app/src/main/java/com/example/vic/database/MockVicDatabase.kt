package com.example.vic.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vic.database.entities.*
import com.example.vic.database.enums.*
import com.example.vic.getCurrentDateTime
import timber.log.Timber


class MockVicDatabase() : VicDatabaseDao {

    private val _customers = MutableLiveData<List<Customer>>(listOf())
    private val _virtualMachines = MutableLiveData<List<VirtualMachine>>(listOf())


    init {
        populateCustomers()
        populateVirtualMachines()
    }

    private fun populateCustomers() {
        val mockCustomers = mutableListOf<Customer>()

        for (id in 1..20) {
            val contact = ContactPerson(
                id.toLong(),
                "firstname-$id",
                "lastname-$id",
                "customer-$id.example@devops.com",
                "phone-number-$id"
            )
            val mock = Customer(id.toLong(), contact, contact)
            mockCustomers.add(mock)
        }

        _customers.value = mockCustomers
    }

    private fun populateVirtualMachines() {
        val mockVirtualMachines = mutableListOf<VirtualMachine>()



        for (id in 1..20) {
            val specifications = Specifications(id + 1, id + 1, id + 1)

            val mock = VirtualMachine(
                id.toLong(),
                "virtual-machine-$id",
                specifications,
                Template.AI,
                Mode.SAAS,
                "devops-vm-$id",
                listOf(Availability.Monday, Availability.Wednesday),
                BackupFrequency.Weekly,
                getCurrentDateTime(),
                Status.ReadyToDeploy,
                "reason-$id",
                listOf(Port(22, "SSH")),
                Host(id.toLong(), "host-$id", specifications),
                listOf(),
                null,
                null,
                null
            )

            mockVirtualMachines.add(mock)
        }

        _virtualMachines.value = mockVirtualMachines
    }

    override fun insertCustomer(customer: Customer): LiveData<Customer> {
        TODO("Not yet implemented")
    }

    override fun getCustomers(): LiveData<List<CustomerIndex>> {
        val customers = _customers.value?.map {
                c -> CustomerIndex(c.id, c.contactPerson.firstName, true)
        }
        Timber.i("customerIndexes is empty: ${customers?.isNotEmpty()}")
        return MutableLiveData(customers)
    }

    override fun getCustomerById(customerId: Long): LiveData<Customer> {
        val customer = _customers.value?.find { it -> it.id == customerId }
        return MutableLiveData(customer)
    }

    override fun getVirtualMachineById(): LiveData<VirtualMachine> {
        TODO("Not yet implemented")
    }
}