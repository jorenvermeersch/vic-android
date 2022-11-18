package com.example.vic.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vic.database.entities.*
import com.example.vic.database.enums.*
import com.example.vic.getCurrentDateTime


class MockVicDatabase() : VicDatabaseDao {

    private val _customers = MutableLiveData<List<Customer>>(listOf())
    private val _virtualMachines = MutableLiveData<List<VirtualMachine>>(listOf())


    init {
        populateVirtualMachines()
        populateCustomers()
    }


    private fun populateCustomers() {
        val mockCustomers = mutableListOf<Customer>()

        for (id in 1..20) {
            val contact = ContactPerson(
                id.toLong(),
                "firstname-$id",
                "lastname-$id",
                "customer-$id.example@devops.com",
                if (id % 3 != 0) "phone-number-$id" else null
            )

            val internal = id in 2..6

            val machines = (_virtualMachines.value ?: listOf())
                .filter { it.id == id.toLong() }
                .map { VirtualMachineIndex(it.id, it.name, it.status) }

            val mock = Customer(
                id.toLong(),
                if (internal) CustomerType.Internal else CustomerType.External,
                contact,
                if (id % 2 == 0) contact else null,
                if (internal) "institution-$id" else null,
                if (internal) "department-$id" else null,
                if (internal) "education-$id" else null,
                if (!internal) "type-$id" else null,
                if (!internal) "company-name-$id" else null,
                machines
            )

            mockCustomers.add(mock)
        }

        _customers.value = mockCustomers
    }

    private fun populateVirtualMachines() {
        val mockVirtualMachines = mutableListOf<VirtualMachine>()
        val today = getCurrentDateTime()

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
                today,
                today,
                today,
                Status.ReadyToDeploy,
                "reason-$id",
                listOf(Port(22, "SSH"), Port(443, "HTTPS")),
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
        val customers = _customers.value?.map { c ->
            CustomerIndex(c.id, c.contactPerson.firstName)
        }
        return MutableLiveData(customers)
    }

    override fun getCustomerById(customerId: Long): LiveData<Customer> {
        val customer = _customers.value?.find { it -> it.id == customerId }
        return MutableLiveData(customer)
    }

    override fun getVirtualMachineById(machineId: Long): LiveData<VirtualMachine> {
        val machine = _virtualMachines.value?.find { it -> it.id == machineId }
        return MutableLiveData(machine)
    }
}