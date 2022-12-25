package com.example.vic.network

// import com.example.vic.screens.login.CredentialsManager
// import androidx.core.content.ContentProviderCompat.requireContext
import com.example.vic.misc.Global
// import com.example.vic.screens.login.CredentialsManager
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class JwtAuthenticator : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        return response.request.newBuilder()
            .header("Authorization", Global.bearer_token)
            .build()
    }
}
