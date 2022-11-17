package com.example.vic.screens.customerdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.vic.R
import com.example.vic.database.entities.Customer
import com.example.vic.database.enums.CustomerType
import com.example.vic.databinding.FragmentCustomerDetailsBinding
import com.example.vic.screens.models.ApplicationViewModel

private val PLACEHOLDER_ID = 1L

class CustomerDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCustomerDetailsBinding
    private val viewModel: ApplicationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_customer_details, container, false)
        binding.viewModel = viewModel


        viewModel.chosenCustomer.observe(viewLifecycleOwner) { customer ->
            updateLayout(customer)
        }


        binding.apply {
            this.customerListButton.setOnClickListener {
                findNavController().navigate(CustomerDetailsFragmentDirections.actionCustomerDetailsFragmentToCustomerListFragment())
            }

            this.virtualMachineDetailsButton.setOnClickListener {
                findNavController().navigate(
                    CustomerDetailsFragmentDirections.actionCustomerDetailsFragmentToVirtualMachineDetailsFragment(
                        PLACEHOLDER_ID
                    )
                )
            }
        }

        return binding.root
    }

    private fun updateLayout(customer: Customer) {
        when (customer.customerType) {
            CustomerType.Internal -> {
                binding.let {
                    it.institution.visibility = View.VISIBLE
                    it.department.visibility = View.VISIBLE
                    it.education.visibility = View.VISIBLE
                    it.externalType.visibility = View.GONE
                    it.companyName.visibility = View.GONE
                }

            }
            CustomerType.External -> {
                binding.let {
                    it.institution.visibility = View.GONE
                    it.department.visibility = View.GONE
                    it.education.visibility = View.GONE
                    it.externalType.visibility = View.VISIBLE
                    it.companyName.visibility = View.VISIBLE
                }
            }
        }

        binding.customerBackupContactInformation.visibility =
            if (customer.backupContactPerson == null) View.INVISIBLE else View.VISIBLE

    }
}