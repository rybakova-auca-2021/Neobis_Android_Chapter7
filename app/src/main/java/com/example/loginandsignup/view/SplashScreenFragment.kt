package com.example.loginandsignup.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import com.example.loginandsignup.R
import com.example.loginandsignup.databinding.FragmentSplashScreenBinding

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : Fragment() {
    private lateinit var binding: FragmentSplashScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigation()
    }

    fun navigation() {
        binding.buttonStart.setOnClickListener {
            findNavController().navigate(R.id.action_splashScreenFragment2_to_registrationFragment)
        }
        binding.haveAnAccount.setOnClickListener {
            findNavController().navigate(R.id.action_splashScreenFragment2_to_loginFragment2)
        }
    }
}