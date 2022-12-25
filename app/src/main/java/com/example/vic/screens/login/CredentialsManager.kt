package com.example.vic.screens.login

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.auth0.android.result.Credentials
// import com.example.vic.network.JwtAuthenticator

object CredentialsManager {
    private const val ACCESS_TOKEN = "access_token"

    private lateinit var editor: SharedPreferences.Editor

    fun saveCredentials(context: Context, credentials: Credentials) {

        val masterKeyAlias: MasterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

        val sp: SharedPreferences = EncryptedSharedPreferences.create(
            context,
            "secret_shared_prefs",
            masterKeyAlias,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        editor = sp.edit()
        editor.putString(ACCESS_TOKEN, credentials.accessToken)
            .apply()

        val jwtParts = credentials.accessToken.split(".")
        val header = jwtParts[0]
        val payload = jwtParts[1]
        val signature = jwtParts[2]

        Log.i("userloggedin", "name: " + credentials.user.name.toString() + " " + credentials.user.email + " " + signature)
    }

    fun getAccessToken(context: Context): String? {
        val masterKeyAlias: MasterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

        val sp: SharedPreferences = EncryptedSharedPreferences.create(
            context,
            "secret_shared_prefs",
            masterKeyAlias,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        return sp.getString(ACCESS_TOKEN, null)
    }
}
