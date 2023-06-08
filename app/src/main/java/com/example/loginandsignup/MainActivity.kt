package com.example.loginandsignup

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val action = intent.action ?: "empty"
        val data = intent.data.toString()

        Log.e("My Tag", action)
        Log.e("My Tag", data)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.hostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        val uri: Uri? = intent.data
        if (uri != null) {
            val params: List<String>? = uri.pathSegments
            val id: String? = params?.get(params.size - 1)
            Toast.makeText(this, "id=$id", Toast.LENGTH_SHORT).show()

            navController.navigate(R.id.additionalInfoFragment2)
        }
    }
}