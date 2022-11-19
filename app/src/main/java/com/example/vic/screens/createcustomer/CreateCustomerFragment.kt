package com.example.vic.screens.createcustomer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.RadioGroup.OnCheckedChangeListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.vic.R
import com.example.vic.database.entities.Customer
import com.example.vic.database.enums.CustomerType
import com.example.vic.databinding.FragmentCreateCustomerBinding
import com.example.vic.screens.models.ApplicationViewModel

class CreateCustomerFragment : Fragment() {

    private lateinit var binding: FragmentCreateCustomerBinding
    private val viewModel: ApplicationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_create_customer, container, false)
        binding.viewModel = viewModel

        binding.customerTypeRadioGroup.setOnCheckedChangeListener { _, itemId ->
            val customerType = determineCustomerType(itemId)
            updateLayout(customerType)
        }

        return binding.root
    }

    private fun determineCustomerType(itemId: Int): CustomerType {
        return when (itemId) {
            R.id.option_external_customer -> CustomerType.External
            else -> CustomerType.Internal

        }
    }

    private fun updateLayout(customerType: CustomerType) {
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
            }
        }
    }
}