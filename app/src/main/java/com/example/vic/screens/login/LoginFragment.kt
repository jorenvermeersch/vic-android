package com.example.vic.screens.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.example.vic.R
import com.example.vic.database.VicDatabase
import com.example.vic.databinding.FragmentLoginBinding
import com.example.vic.screens.models.ApplicationViewModel
import com.example.vic.screens.models.ApplicationViewModelFactory

class LoginFragment : Fragment() {
    private lateinit var account: Auth0
    private lateinit var binding: FragmentLoginBinding
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
            DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.viewModel = viewModel

        hideToolbar()

        account = Auth0(
            getString(R.string.client_id),
            getString(R.string.com_auth0_domain)
        )
        binding.loginButton.setOnClickListener {
            loginWithBrowser()
        }

        return binding.root
    }

    private fun hideToolbar() {
        // Make toolbar invisible and inaccessible when not logged in.
        val toolbar = requireActivity().findViewById(R.id.toolbar) as Toolbar
        toolbar.visibility = View.GONE
    }

    private fun loginWithBrowser() {
        // Setup the WebAuthProvider, using the custom scheme and scope.

        WebAuthProvider.login(account)
            .withScheme("demo")
            .withScope("openid profile email")
            // Launch the authentication passing the callback where the results will be received
            .start(
                requireContext(),
                object : Callback<Credentials, AuthenticationException> {
                    // Called when there is an authentication failure
                    override fun onFailure(error: AuthenticationException) {
                        Toast.makeText(context, "Login failed.", Toast.LENGTH_SHORT).show()
                    }

                    // Called when authentication completed successfully
                    override fun onSuccess(result: Credentials) {
                        // Get the access token from the credentials object.
                        // This can be used to call APIs
                        CredentialsManager.saveCredentials(requireContext(), result)
                        Toast.makeText(context, "Login successful.", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToCustomerListFragment())
                    }
                }
            )
    }
}
