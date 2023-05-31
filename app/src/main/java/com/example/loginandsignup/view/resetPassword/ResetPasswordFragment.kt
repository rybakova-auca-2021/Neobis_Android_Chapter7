package com.example.loginandsignup.view.resetPassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.loginandsignup.R
import com.example.loginandsignup.databinding.FragmentResetPasswordBinding

class ResetPasswordFragment : Fragment() {
    private lateinit var binding: FragmentResetPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigation()
    }

    fun navigation() {
        binding.buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_resetPasswordFragment_to_resetSendToEmailFragment)
        }
        binding.loginButton2.setOnClickListener{
            findNavController().navigate(R.id.action_resetPasswordFragment_to_loginFragment2)
        }
    }
}