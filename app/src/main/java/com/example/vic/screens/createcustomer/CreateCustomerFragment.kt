package com.example.vic.screens.createcustomer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.vic.R
import com.example.vic.database.VicDatabase
import com.example.vic.databinding.FragmentCreateCustomerBinding
import com.example.vic.domain.entities.ContactPerson
import com.example.vic.domain.enums.CustomerType
import com.example.vic.misc.Global
import com.example.vic.network.ApiCustomer
import com.example.vic.screens.models.ApplicationViewModel
import com.example.vic.screens.models.ApplicationViewModelFactory

class CreateCustomerFragment : Fragment() {

    private lateinit var binding: FragmentCreateCustomerBinding
    private val viewModel: ApplicationViewModel by activityViewModels {
        val appContext = requireNotNull(this.activity).application
        val dataSource = VicDatabase.getInstance(appContext).customerIndexDatabaseDao
        ApplicationViewModelFactory(dataSource, appContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_create_customer, container, false)
        binding.viewModel = viewModel

        configureCustomerTypeSelector()
        configureFormSubmit()

        if (Global.isDevelopment) {
            binding.let {
                it.contactFirstname.setText("Robin")
                it.contactLastname.setText("Vermeir")
                it.contactEmail.setText("kevinvermeire827@gmail.com")
                it.contactPhoneNumber.setText("(982) 537-8024")

                it.backupContactFirstname.setText("Kerem")
                it.backupContactLastname.setText("Yilmaz")
                it.backupContactEmail.setText("kevinvermeire827_2@gmail.com")
                it.backupContactPhoneNumber.setText("(985) 386-4011")

                // EXTERNAL
                it.externalType.setText("Unizo")
                it.companyName.setText("Schouten BV")

                // INTERNAL
                it.institution.setText("Hogent")
                it.department.setText("Meer, Vries and Kok")
                it.education.setText("Elektro-mechanica")
            }
        }

        return binding.root
    }

    private fun configureCustomerTypeSelector() {
        // Fields are added or removed based on type of customer.
        binding.customerTypeRadioGroup.setOnCheckedChangeListener { _, itemId ->
            val customerType = determineCustomerType(itemId)
            updateForm(customerType)
        }
    }

    private fun configureFormSubmit() {
        binding.addCustomerButton.setOnClickListener {
            // TODO: Add Customer using ApplicationViewModel.

            var contactPerson: ContactPerson
            var backupContactPerson: ContactPerson

            var type: Int? = null
            var customer: ApiCustomer

            binding.let {
                type = if (it.optionInternalCustomer.isChecked) 0 else 1

                if (type == 0) {
                    customer = ApiCustomer(
                        id = 0,
                        companyName = null,
                        companyType = null,
                        institution = 0, // TODO: institution is still static
                        department = it.department.toString(),
                        edu = it.education.toString(),
                        customerType = 0,
                        apiContactPerson = null,
                        apiBackupContactPerson = null,
                        virtualMachines = null
                    )
                } else {
                    customer = ApiCustomer(
                        id = 0,
                        companyName = it.companyName.toString(),
                        companyType = it.externalType.toString(),
                        institution = null,
                        department = null,
                        edu = null,
                        customerType = 1,
                        apiContactPerson = null,
                        apiBackupContactPerson = null,
                        virtualMachines = null
                    )
                }
            }

            binding.let {
                contactPerson = ContactPerson(
                    1,
                    it.contactFirstname.text.toString(),
                    it.contactLastname.text.toString(),
                    it.contactEmail.text.toString(),
                    it.contactPhoneNumber.text.toString(),
                )

                backupContactPerson = ContactPerson(
                    1,
                    it.backupContactFirstname.text.toString(),
                    it.backupContactLastname.text.toString(),
                    it.backupContactEmail.text.toString(),
                    it.backupContactPhoneNumber.text.toString(),
                )
            }

            Log.i("customercreateoutput", type.toString())
            Log.i("customercreateoutput", contactPerson.toString())

            viewModel.createCustomer(customer)
        }
    }

    private fun determineCustomerType(itemId: Int): CustomerType {
        return when (itemId) {
            R.id.option_external_customer -> CustomerType.External
            else -> CustomerType.Internal
        }
    }

    private fun updateForm(customerType: CustomerType) {
        binding.let {
            when (customerType) {
                CustomerType.Internal -> {
                    it.generalInternalCustomer.visibility = View.VISIBLE
                    it.generalExternalCustomer.visibility = View.GONE
                }
                CustomerType.External -> {
                    it.generalInternalCustomer.visibility = View.GONE
                    it.generalExternalCustomer.visibility = View.VISIBLE
                }
                else -> {
                    it.generalInternalCustomer.visibility = View.GONE
                    it.generalExternalCustomer.visibility = View.GONE
                }
            }
        }
    }
}
