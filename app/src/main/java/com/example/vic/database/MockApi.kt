package com.example.vic.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vic.domain.entities.Account
import com.example.vic.domain.entities.ContactPerson
import com.example.vic.domain.entities.Credentials
import com.example.vic.domain.entities.Customer
import com.example.vic.domain.entities.CustomerIndex
import com.example.vic.domain.entities.Host
import com.example.vic.domain.entities.Port
import com.example.vic.domain.entities.Specifications
import com.example.vic.domain.entities.VirtualMachine
import com.example.vic.domain.entities.VirtualMachineIndex
import com.example.vic.domain.enums.Availability
import com.example.vic.domain.enums.BackupFrequency
import com.example.vic.domain.enums.CustomerType
import com.example.vic.domain.enums.Mode
import com.example.vic.domain.enums.Role
import com.example.vic.domain.enums.Status
import com.example.vic.domain.enums.Template
import com.example.vic.getCurrentDateTime

class MockApi {

    private val _customers = MutableLiveData<List<Customer>>(listOf())
    private val _virtualMachines = MutableLiveData<List<VirtualMachine>>(listOf())

    init {
        populate()
    }

    private fun populate() {
        val mockCustomers = generateMockCustomers()
        val mockVirtualMachines = generateMockVirtualMachines()

        for (index in mockCustomers.indices) {
            val mockCustomer = mockCustomers[index]
            val machine1 = mockVirtualMachines[index]
            val machine2 = mockVirtualMachines[index + 1]

            mockCustomer.virtualMachines =
                listOf(machine1, machine2).map { VirtualMachineIndex(it.id, it.name, it.status) }
            machine1.requester = mockCustomer
            machine1.user = mockCustomer
            machine2.requester = mockCustomer
            machine2.user = mockCustomer
        }

        _customers.value = mockCustomers
        _virtualMachines.value = mockVirtualMachines
    }

    private fun generateMockCustomers(): List<Customer> {
        val mockCustomers = mutableListOf<Customer>()

        for (id in 1..10) {
            val internal = id in 2..6

            val contact = ContactPerson(
                id.toLong(),
                "firstname-$id",
                "lastname-$id",
                "customer-$id.example@devops.com",
                if (id % 3 != 0) "phone-number-$id" else null
            )

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
                listOf()
            )
            mockCustomers.add(mock)
        }

        return mockCustomers
    }

    private fun generateMockVirtualMachines(): List<VirtualMachine> {
        val mockVirtualMachines = mutableListOf<VirtualMachine>()

        val today = getCurrentDateTime()
        val account = Account(
            1,
            "firstname-1",
            "lastname-1",
            "email-1",
            Role.Admin,
            "password-1",
            true,
            "department-1",
            "education-1"
        )

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
                if (id % 2 == 0) Status.Deployed else Status.InProgress,
                "reason-$id",
                listOf(Port(22, "SSH"), Port(443, "HTTPS")),
                Host(id.toLong(), "host-$id", specifications),
                listOf(
                    Credentials("user-$id", "password-$id", "role-$id"),
                    Credentials("user-$id", "password-$id", "role-$id"),
                    Credentials("user-$id", "password-$id", "role-$id"),
                    Credentials("user-$id", "password-$id", "role-$id")
                ),
                account,
                null,
                null
            )
            mockVirtualMachines.add(mock)
        }

        return mockVirtualMachines
    }

    fun getCustomers(): LiveData<List<CustomerIndex>> {
        val customers = _customers.value?.map { c ->
            CustomerIndex(c.id, c.contactPerson.firstName)
        }
        return MutableLiveData(customers)
    }

    fun getCustomerById(customerId: Long): LiveData<Customer> {
        val customer = _customers.value?.find { it -> it.id == customerId }
        return MutableLiveData(customer)
    }

    fun getVirtualMachineById(machineId: Long): LiveData<VirtualMachine> {
        val machine = _virtualMachines.value?.find { it -> it.id == machineId }
        return MutableLiveData(machine)
    }
}
