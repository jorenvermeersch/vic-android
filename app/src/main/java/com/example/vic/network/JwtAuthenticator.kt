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
//        val credentials = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IlhMSm9EODdoYlRoRHVjd2ZqZjN4ZiJ9.eyJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3JvbGUiOlsiTWFzdGVyIl0sImlzcyI6Imh0dHBzOi8vZGV2LWFzaG1ncnpoNGdmbmZhMTgudXMuYXV0aDAuY29tLyIsInN1YiI6ImF1dGgwfDYzOGUzMmQ0MmUzNDI1MDRhZTkyYjhlOCIsImF1ZCI6WyJodHRwczovL2FwaS52aWMuY29tIiwiaHR0cHM6Ly9kZXYtYXNobWdyemg0Z2ZuZmExOC51cy5hdXRoMC5jb20vdXNlcmluZm8iXSwiaWF0IjoxNjcxOTgxMjgxLCJleHAiOjE2NzIwNjc2ODEsImF6cCI6InBacjNqOTNkSGlrU1JPWDFIMVY3UzVENm1XbUJZMXFyIiwic2NvcGUiOiJvcGVuaWQgcHJvZmlsZSJ9.XZx9xv0vW4v2tEAByoecjqKM6ajJuGoUo4PwJS2oNB_GbDbOdcduSJcXWajiyls9arLIxZi_j5aEPvcYvdP7rQD_Z-G5M-r8kLo6xnMn_mojWdnonWps0VC7B_B55mSUH8mWmayHYuYadhIdako7EIAKWRq4r9dR96SVUNQ6X01Nm9Vqs_Yjj-wkdQ-LgqNh6dBebcE0EiW-T3K71onLHqfV5cJwgnCbF7uNiWiHs_q7WI1NAlZ2PN1UD3VS6_spnSc-lH7wMmmELs40-Veu4PuaVz4heGFucT1aiLpgpbM2F_3V24GIkQ3nzWT2IJRG2F4eBxG63qsmHE5A2hrEkQ"
//         val credentials = CredentialsManager.getAccessToken(requireContext())
        return response.request.newBuilder()
            .header("Authorization", Global.bearer_token)
            .build()
    }
}
