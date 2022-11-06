package com.example.vic.screens.customerlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.vic.R
import com.example.vic.databinding.FragmentCustomerListBinding

class CustomerListFragment : Fragment() {

    private lateinit var binding: FragmentCustomerListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_customer_list, container, false)

        // TODO: Add SafeArgs.
        binding.apply {
            this.toCustomerDetailsButton.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_customerListFragment_to_customerDetailsFragment)
            )

            this.createCustomerButton.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_customerListFragment_to_createCustomerFragment)
            )
        }


        return binding.root
    }
}