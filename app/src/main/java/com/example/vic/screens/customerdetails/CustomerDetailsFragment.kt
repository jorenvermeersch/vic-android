package com.example.vic.screens.customerdetails

import android.os.Bundle
import android.util.Log
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
import com.example.vic.misc.GlobalMethods
import com.example.vic.screens.customerlist.CustomerListFragmentDirections
import com.example.vic.screens.models.ApplicationViewModel
import com.example.vic.screens.models.ApplicationViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CustomerDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCustomerDetailsBinding
    private val viewModel: ApplicationViewModel by activityViewModels {
        val appContext = requireNotNull(this.activity).application
        val dataSource = VicDatabase.getInstance(appContext).customerIndexDatabaseDao
        ApplicationViewModelFactory(dataSource, appContext)
    }

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_customer_details, container, false)
        binding.viewModel = viewModel

        viewModel.chosenCustomer.observe(viewLifecycleOwner) { customer ->
            customer?.let {
                updateLayout(customer)
            }
        }
        setVirtualMachineList()
        return binding.root
    }

    private fun setVirtualMachineList() {
        val adapter = VirtualMachineIndexAdapter(
            VirtualMachineIndexListener { machineId ->
                coroutineScope.launch {
                    try {
                        viewModel.findVirtualMachine(machineId).await()
                        findNavController().navigate(
                            when (GlobalMethods.isOnline(requireActivity().application)) {
                                true -> CustomerDetailsFragmentDirections.actionCustomerDetailsFragmentToVirtualMachineDetailsFragment(
                                    machineId
                                )
                                false -> CustomerListFragmentDirections.actionCustomerListFragmentToInternetfailure()
                            }
                        )
                        Log.i("Fetch Success", "Data was fetched and will be shown")
                    } catch (e: Exception) {
                        Log.i("Error while fetching the customer details: ", e.message.toString())
                    }
                }
            }
        )

        binding.virtualMachineList.adapter = adapter

        viewModel.chosenCustomer.observe(viewLifecycleOwner) { customer ->
            adapter.submitList(customer?.virtualMachines)
        }
    }

    private fun updateLayout(customer: Customer) {

        Log.i("ONLINE?: " + GlobalMethods.isOnline(requireNotNull(this.activity).application), "test")

        if (GlobalMethods.isOnline(requireNotNull(this.activity).application) == false) {
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
