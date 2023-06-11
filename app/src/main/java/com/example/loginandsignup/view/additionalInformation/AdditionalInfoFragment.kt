package com.example.loginandsignup.view.additionalInformation

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.loginandsignup.R
import com.example.loginandsignup.util.Utils
import com.example.loginandsignup.api.ApiInterface
import com.example.loginandsignup.api.RetrofitInstance
import com.example.loginandsignup.databinding.FragmentAdditionalInfoBinding
import com.example.loginandsignup.model.PersonalInfoRequest
import com.example.loginandsignup.response.EmailVerificationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdditionalInfoFragment : Fragment() {
    private lateinit var binding: FragmentAdditionalInfoBinding
    private val apiInterface: ApiInterface by lazy { RetrofitInstance.api }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdditionalInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupOnClickListeners()
        validateInputFields()
        emailVerification()
    }

    private fun isPhoneValid(phone: String): Boolean {
        val pattern = Patterns.PHONE
        return pattern.matcher(phone).matches()
    }

    @SuppressLint("ResourceAsColor")
    private fun validateInputFields() {
        val name = binding.inputRegNameProfile.text?.toString() ?: ""
        val surname = binding.inputRegSurnameProfile.text?.toString() ?: ""
        val birthDate = binding.inputRegBirthProfile.text?.toString() ?: ""
        val phone = binding.inputPhoneNumber.text?.toString() ?: ""

        val isValid = name.isNotEmpty() && surname.isNotEmpty() && birthDate.isNotEmpty() && isPhoneValid(phone)

        if (isValid) {
            binding.registration.setBackgroundResource(R.color.invalidButtonColor)
            binding.registration.isEnabled = false
        } else {
            binding.registration.setBackgroundResource(R.color.validButtonColor)
            binding.registration.isEnabled = true
        }
    }

    private fun setupOnClickListeners() {
        binding.registration.setOnClickListener {
            val name = binding.inputRegNameProfile.text.toString()
            val surname = binding.inputRegSurnameProfile.text.toString()
            val birthDate = binding.inputRegBirthProfile.text.toString()
            val phone = binding.inputPhoneNumber.text.toString()

            val personalInfoRequest = PersonalInfoRequest(name, surname, birthDate, phone)

            registerPersonalInfo(personalInfoRequest)
        }
    }

    private fun emailVerification() {
        val tokenEmail = Utils.tokenEmail
        val apiInterface = RetrofitInstance.api

        val call = apiInterface.verifyEmail(tokenEmail)
        call.enqueue(object : Callback<EmailVerificationResponse> {
            override fun onResponse(
                call: Call<EmailVerificationResponse>,
                response: Response<EmailVerificationResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Email successfully activated", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Failed to activate", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<EmailVerificationResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Failed. Please try again.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun registerPersonalInfo(request: PersonalInfoRequest) {
        val email = Utils.email
        email.let {
            apiInterface.registerPersonalInfo(request, it).enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if(response.isSuccessful) {
                            findNavController().navigate(R.id.action_additionalInfoFragment2_to_createPasswordFragment2)
                        } else {
                            Toast.makeText(requireContext(), "Failed. Please try again.", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(requireContext(), "Failed. Please try again.", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }
}