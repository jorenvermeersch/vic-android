package com.example.vic.screens.customerlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.vic.R
import com.example.vic.databinding.FragmentCustomerListBinding
import com.example.vic.screens.models.ApplicationViewModel
import timber.log.Timber

private val PLACEHOLDER_ID = 1L

class CustomerListFragment : Fragment() {

    private lateinit var binding: FragmentCustomerListBinding
    private val viewModel: ApplicationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_customer_list, container, false)
        binding.viewModel = viewModel

        // Binding adapter for RecyclerView.
        val adapter = CustomerIndexAdapter()
        binding.customerList.adapter = adapter

        viewModel.customers.observe(viewLifecycleOwner, Observer { customers ->
            customers?.let {
                adapter.submitList(customers)
            }
            Timber.i("Customers changed")
        })


        binding.apply {
            this.toCustomerDetailsButton.setOnClickListener {
                findNavController().navigate(
                    CustomerListFragmentDirections.actionCustomerListFragmentToCustomerDetailsFragment(
                        PLACEHOLDER_ID
                    )
                )
            }

            this.createCustomerButton.setOnClickListener {
                findNavController().navigate(CustomerListFragmentDirections.actionCustomerListFragmentToCreateCustomerFragment())
            }
        }

        return binding.root
    }
}