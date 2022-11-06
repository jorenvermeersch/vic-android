package com.example.vic.screens.createcustomer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.vic.R
import com.example.vic.databinding.FragmentCreateCustomerBinding

class CreateCustomerFragment : Fragment() {

    private lateinit var binding: FragmentCreateCustomerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_create_customer, container, false)

        return binding.root
    }
}