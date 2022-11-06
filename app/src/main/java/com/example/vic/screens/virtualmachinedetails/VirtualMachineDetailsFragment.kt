package com.example.vic.screens.virtualmachinedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.vic.R
import com.example.vic.databinding.FragmentVirtualMachineDetailsBinding

class VirtualMachineDetailsFragment : Fragment() {

    private lateinit var binding : FragmentVirtualMachineDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_virtual_machine_details, container, false)
        return binding.root
    }
}