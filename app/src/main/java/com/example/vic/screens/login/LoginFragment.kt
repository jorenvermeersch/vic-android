package com.example.vic.screens.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.vic.R
import com.example.vic.database.VicDatabase
import com.example.vic.databinding.FragmentLoginBinding
import com.example.vic.screens.models.ApplicationViewModel
import com.example.vic.screens.models.ApplicationViewModelFactory

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: ApplicationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        // Use a factory to pass the database reference to the viewModel.
//        val appContext = requireNotNull(this.activity).application
//        val dataSource = VicDatabase.getInstance(appContext).customerIndexDao
//        val viewModelFactory = ApplicationViewModelFactory(dataSource, appContext)
//
//        viewModel = ViewModelProvider(this, viewModelFactory)[ApplicationViewModel::class.java]

        binding.viewModel = viewModel


        hideToolbar()


        binding.loginButton.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToCustomerListFragment())
        }

        return binding.root
    }

    private fun hideToolbar() {
        // Make toolbar invisible and inaccessible when not logged in.
        val toolbar = requireActivity().findViewById(R.id.toolbar) as Toolbar
        toolbar.visibility = View.GONE
    }




}