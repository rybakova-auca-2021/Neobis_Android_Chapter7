package com.example.loginandsignup.view.additionalInformation

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.loginandsignup.R
import com.example.loginandsignup.databinding.FragmentAdditionalInfoBinding

class AdditionalInfoFragment : Fragment() {
    private lateinit var binding: FragmentAdditionalInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdditionalInfoBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigation()
        validateInputFields()
    }

    private fun isEmailValid(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    @SuppressLint("ResourceAsColor")
    private fun validateInputFields() {
        val name = binding.inputRegNameProfile.text?.toString() ?: ""
        val surname = binding.inputRegSurnameProfile.text?.toString() ?: ""
        val birthDate = binding.inputRegBirthProfile.text?.toString() ?: ""
        val email = binding.inputEmailProfile.text?.toString() ?: ""

        val isValid = name.isNotEmpty() && surname.isNotEmpty() && birthDate.isNotEmpty() && isEmailValid(email)

        // Change button color based on validity
        if (isValid) {
            binding.registration.setBackgroundColor(R.color.validButtonColor)
            binding.registration.isEnabled = true
        } else {
            binding.registration.setBackgroundColor(R.color.invalidButtonColor)
            binding.registration.isEnabled = false
        }
    }

    private fun navigation() {
        binding.registration.setOnClickListener {
            findNavController().navigate(R.id.action_additionalInfoFragment2_to_createPasswordFragment2)
        }
    }
}