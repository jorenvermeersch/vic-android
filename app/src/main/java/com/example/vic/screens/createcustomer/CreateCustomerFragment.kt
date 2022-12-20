package com.example.vic.screens.createcustomer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.vic.R
import com.example.vic.database.VicDatabase
import com.example.vic.databinding.FragmentCreateCustomerBinding
import com.example.vic.domain.enums.CustomerType
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

            binding.let {
//                val contactPerson = ContactPerson(
//                    1,
//                    it.contactFirstname.text.toString(),
//                    it.contactLastname.text.toString(),
//                    it.contactEmail.text.toString(),
//                    it.contactPhoneNumber.text.toString(),
//                )
//
//                val backupContactPerson = ContactPerson(
//                    1,
//                    it.backupContactFirstname.text.toString(),
//                    it.backupContactLastname.text.toString(),
//                    it.backupContactEmail.text.toString(),
//                    it.backupContactPhoneNumber.text.toString(),
//                )
            }
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
