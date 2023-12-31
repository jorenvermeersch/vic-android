package com.example.vic.screens.virtualmachinedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.vic.R
import com.example.vic.database.VicDatabase
import com.example.vic.databinding.FragmentVirtualMachineDetailsBinding
import com.example.vic.screens.models.ApplicationViewModel
import com.example.vic.screens.models.ApplicationViewModelFactory

class VirtualMachineDetailsFragment : Fragment() {

    private lateinit var binding: FragmentVirtualMachineDetailsBinding
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
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_virtual_machine_details,
            container,
            false
        )
        binding.viewModel = viewModel

        setCredentialsList()

        return binding.root
    }

    private fun setCredentialsList() {
        val adapter = CredentialsAdapter()
        binding.credentialsList.adapter = adapter

        viewModel.chosenVirtualMachine.observe(viewLifecycleOwner) {
            adapter.submitList(it!!.credentials)
        }
    }
}
