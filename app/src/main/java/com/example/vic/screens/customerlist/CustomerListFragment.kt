package com.example.vic.screens.customerlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
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

        // Binding adapter for RecyclerView with onClickListener.
        val adapter = CustomerIndexAdapter(CustomerIndexListener { customerId ->
            viewModel.onCustomerClicked(customerId)

            findNavController().navigate(
                CustomerListFragmentDirections.actionCustomerListFragmentToCustomerDetailsFragment(
                    customerId
                )
            )
        })
        binding.customerList.adapter = adapter

        // Display customers in RecyclerView.
        viewModel.filteredCustomers.observe(viewLifecycleOwner, Observer { customers ->
            customers?.let {
                adapter.submitList(customers)
            }
        })

        // Search bar.
        binding.customerSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.onCustomerSearch(it)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        // Reset customers after removing query.
        binding.customerSearch.setOnCloseListener {
            viewModel.resetCustomerSearch()
            false
        }

        return binding.root
    }
}