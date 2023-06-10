package com.example.loginandsignup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment

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
}