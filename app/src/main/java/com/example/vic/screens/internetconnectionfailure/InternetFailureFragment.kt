package com.example.vic.screens.internetconnectionfailure

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
import com.example.vic.databinding.FragmentInternetfailureBinding
import com.example.vic.screens.models.ApplicationViewModel
import com.example.vic.screens.models.ApplicationViewModelFactory

class InternetFailureFragment : Fragment() {

    private lateinit var binding: FragmentInternetfailureBinding
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
            DataBindingUtil.inflate(inflater, R.layout.fragment_internetfailure, container, false)

        binding.let {
            it.goback.setOnClickListener {
                findNavController().navigate(
                    InternetFailureFragmentDirections.actionInternetfailureToCustomerListFragment2()
                )
            }
        }

        return binding.root
    }
}