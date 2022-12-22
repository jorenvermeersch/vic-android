package com.example.vic.screens.customerdetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.vic.R
import com.example.vic.database.VicDatabase
import com.example.vic.databinding.FragmentCustomerDetailsBinding
import com.example.vic.domain.entities.Customer
import com.example.vic.domain.enums.CustomerType
import com.example.vic.misc.Global
import com.example.vic.screens.customerlist.CustomerListFragmentDirections
import com.example.vic.screens.models.ApplicationViewModel
import com.example.vic.screens.models.ApplicationViewModelFactory
import timber.log.Timber

class CustomerDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCustomerDetailsBinding
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
            DataBindingUtil.inflate(inflater, R.layout.fragment_customer_details, container, false)
        binding.viewModel = viewModel
        setButtons()
        setLayout()
        setVirtualMachineList()

        return binding.root
    }

    private fun composeEmail(Address: Array<String>, subject: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            for (item in Address) {
                Timber.tag("Address").i(item)
            }
            data = Uri.parse("mailto:") // only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, Address)
            putExtra(Intent.EXTRA_SUBJECT, subject)
        }
        startActivity(intent)
    }

    private fun callNumber(phone: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
        startActivity(intent)
    }

    private fun setLayout() {
        // Change layout based on type of customer.
        viewModel.chosenCustomer.observe(viewLifecycleOwner) { customer ->
            customer?.let {
                updateLayout(customer)
            }
        }
    }

    private fun setButtons() {
        binding.sendMailButton.setOnClickListener { composeEmail(arrayOf(binding.customerEmail.text.toString()), "-Onderwerp-") }
        binding.sendBackupmailButton.setOnClickListener { composeEmail(arrayOf(binding.backupEmail.text.toString()), "-Onderwerp-") }
        binding.callCustomerButton.setOnClickListener { callNumber(binding.customerTel.text.toString()) }
        binding.callBackupButton.setOnClickListener { callNumber(binding.backupTel.text.toString()) }
    }

    private fun setVirtualMachineList() {
        val adapter = VirtualMachineIndexAdapter(
            VirtualMachineIndexListener { machineId ->
                try {
                    viewModel.findVirtualMachine(machineId)
                    findNavController().navigate(
                        when (Global.isOnline(requireActivity().application)) {
                            true -> CustomerDetailsFragmentDirections.actionCustomerDetailsFragmentToVirtualMachineDetailsFragment(machineId)
                            false -> CustomerListFragmentDirections.actionCustomerListFragmentToInternetfailure()
                        }
                    )
                } catch (e: Exception) {
                    Timber.i("Error while fetching the customer details: ", e.message.toString())
                }
            }
        )

        binding.virtualMachineList.adapter = adapter

        viewModel.chosenCustomer.observe(viewLifecycleOwner) { customer ->
            adapter.submitList(customer?.virtualMachines)
        }
    }

    private fun updateLayout(customer: Customer) {
        if (!Global.isOnline(requireNotNull(this.activity).application)) {
            binding.let {
                it.institution.visibility = View.GONE
                it.department.visibility = View.GONE
                it.education.visibility = View.GONE
                it.externalType.visibility = View.GONE
                it.companyName.visibility = View.GONE
            }
        } else {
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
                CustomerType.Unknown -> {
                    binding.let {
                        it.institution.visibility = View.GONE
                        it.department.visibility = View.GONE
                        it.education.visibility = View.GONE
                        it.externalType.visibility = View.GONE
                        it.companyName.visibility = View.GONE
                    }
                }
                else -> {
                    binding.let {
                        it.institution.visibility = View.GONE
                        it.department.visibility = View.GONE
                        it.education.visibility = View.GONE
                        it.externalType.visibility = View.GONE
                        it.companyName.visibility = View.GONE
                    }
                }
            }
        }

        binding.customerBackupContactInformation.visibility =
            if (customer.backupContactPerson == null) View.GONE else View.VISIBLE
    }
}
