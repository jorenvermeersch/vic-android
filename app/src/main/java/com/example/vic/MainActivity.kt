package com.example.vic

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.example.vic.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var account: Auth0
    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        toolbar = binding.toolbar as Toolbar // as Toolbar is needed. App crashes otherwise.
        setSupportActionBar(toolbar)
        account = Auth0(
            getString(R.string.client_id),
            getString(R.string.com_auth0_domain)
        )

        // TODO: Not working properly.
        this.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.overflow_menu, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    when (menuItem.itemId) {
                        R.id.loginFragment -> {
                            logout()
                            findNavController(R.id.navHostFragment).navigate(R.id.loginFragment)
                            findNavController(R.id.navHostFragment).popBackStack(R.id.loginFragment, true)

                        }
                    }
                    return NavigationUI.onNavDestinationSelected(
                        menuItem,
                        findNavController(R.id.navHostFragment)
                    )
                }
            },
            this
        )
    }

    override fun onBackPressed() {
        if(findNavController(R.id.navHostFragment).currentDestination?.id == R.id.loginFragment) {
            finish()
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun logout() {
        WebAuthProvider.logout(account)
            .withScheme("demo")
            .start(
                this,
                object : Callback<Void?, AuthenticationException> {
                    override fun onSuccess(result: Void?) {
                        Toast.makeText(
                            this@MainActivity,
                            "Logout successful",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onFailure(error: AuthenticationException) {
                        Toast.makeText(
                            this@MainActivity,
                            "Logout failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            )
    }
}
