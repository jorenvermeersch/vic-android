package com.example.vic.screens.customerlist

import android.os.Bundle
import android.util.Log
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
import com.example.vic.misc.GlobalMethods
import com.example.vic.network.ApiCustomerContainer
import com.example.vic.network.CustomerApi
import com.example.vic.network.asDomainModel
import com.example.vic.screens.models.ApplicationViewModel
import com.example.vic.screens.models.ApplicationViewModelFactory
import kotlin.coroutines.coroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class CustomerListFragment : Fragment() {

    private lateinit var binding: FragmentCustomerListBinding
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
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_customer_list, container, false)

//        val application = requireNotNull(this.activity).application
//        val datasource = VicDatabase.getInstance(application).customerIndexDatabaseDao
//
//        val viewModelFactory = CustomerListViewModelFactory(datasource, application)
//        val viewModel = ViewModelProvider(this, viewModelFactory).get(CustomerListViewModel::class.java)

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
                    coroutineScope.launch {
                        try {
                            viewModel.findCustomer(customerId).await()
                            findNavController().navigate(
                                when (GlobalMethods.isOnline(requireActivity().application)) {
                                    true -> CustomerListFragmentDirections.actionCustomerListFragmentToCustomerDetailsFragment(customerId)
                                    false -> CustomerListFragmentDirections.actionCustomerListFragmentToInternetfailure()
                                }
                            )
                            Log.i("Fetch Success", "Data was fetched and will be shown")
                        } catch (e: Exception) {
                            Log.i("Error while fetching the customer details: ", e.message.toString())
                        }
                    }


                viewModel.chosenCustomer.observe(viewLifecycleOwner) {
                    findNavController().navigate(
                        when (GlobalMethods.isOnline(requireActivity().application)) {
                            true -> CustomerListFragmentDirections.actionCustomerListFragmentToCustomerDetailsFragment(customerId)
                            false -> CustomerListFragmentDirections.actionCustomerListFragmentToInternetfailure()
                        }
                    )
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
