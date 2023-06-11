package com.example.loginandsignup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.example.loginandsignup.util.Utils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.hostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        val uri = intent.data?.toString()

        val tokenForVerifying = getToken(uri)
        if (tokenForVerifying != null) {
            Utils.tokenEmail = tokenForVerifying
        }

        val idForResetting = getIdForResetting(uri)
        if(idForResetting != null) {
            Utils.uidb64 = idForResetting
        }

        val tokenForResetting = getTokenForResetting(uri)
        if(tokenForResetting != null) {
            Utils.token = tokenForResetting
        }

        if (uri != null) {
            if(uri.contains("auth/password-reset")) {
                navController.navigate(R.id.resetPasswordFragment)
            } else {
                navController.navigate(R.id.additionalInfoFragment2)
            }
        }
    }
    private fun getToken(uri: String?) : String? {
        var token = ""
        if (uri != null && uri.contains("auth/email-verify")) {
            token = uri.removePrefix("http://35.234.124.146/auth/email-verify/?token=")
        }
        return token
    }

    private fun getIdForResetting(uri: String?): String? {
        var id: String? = null
        if (uri != null && uri.contains("/auth/password-reset/")) {
            val segments = uri.split("/")
            if (segments.size >= 6) {
                id = segments[5]
            }
        }
        return id
    }

    private fun getTokenForResetting(uri: String?): String? {
        var token: String? = null
        if (uri != null && uri.contains("/auth/password-reset/")) {
            val segments = uri.split("/")
            if (segments.size >= 6) {
                token = segments[6]
            }
        }
        return token
    }
}