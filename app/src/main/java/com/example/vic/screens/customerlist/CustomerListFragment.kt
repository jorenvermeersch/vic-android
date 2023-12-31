package com.example.vic.screens.customerlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.vic.R
import com.example.vic.database.VicDatabase
import com.example.vic.databinding.FragmentCustomerListBinding
import com.example.vic.misc.Global
import com.example.vic.screens.models.ApplicationViewModel
import com.example.vic.screens.models.ApplicationViewModelFactory
import timber.log.Timber

class CustomerListFragment : Fragment() {

    private lateinit var binding: FragmentCustomerListBinding
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
            DataBindingUtil.inflate(inflater, R.layout.fragment_customer_list, container, false)

        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        showToolbar()
        configureSearchView()
        setCustomerList()

        return binding.root
    }

    private fun showToolbar() {
        // Make toolbar visible after logging in.
        val toolbar = requireActivity().findViewById(R.id.toolbar) as Toolbar
        toolbar.visibility = View.VISIBLE
    }

    private fun configureSearchView() {
        // Update displayed customers when searching.
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
    }

    private fun setCustomerList() {
        val adapter = CustomerIndexAdapter(
            CustomerIndexListener { customerId ->
                try {
                    viewModel.findCustomer(customerId)
                    findNavController().navigate(
                        when (Global.isOnline(requireActivity().application) && viewModel.allCustomers.value != null) {
                            true -> CustomerListFragmentDirections.actionCustomerListFragmentToCustomerDetailsFragment(customerId)
                            false -> CustomerListFragmentDirections.actionCustomerListFragmentToInternetfailure()
                        }
                    )
                } catch (e: Exception) {
                    Timber.i("Error while fetching the customer details: ", e.message.toString())
                }
            }
        )

        binding.customerList.adapter = adapter

        // Display customers in RecyclerView.
        viewModel.filteredCustomers.observe(viewLifecycleOwner) { customers ->
            customers?.let {
                adapter.submitList(customers)
            }
        }
    }
}
