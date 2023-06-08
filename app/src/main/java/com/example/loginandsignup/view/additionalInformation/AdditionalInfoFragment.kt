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
import com.example.loginandsignup.api.ApiInterface
import com.example.loginandsignup.api.RetrofitInstance
import com.example.loginandsignup.databinding.FragmentAdditionalInfoBinding
import com.example.loginandsignup.model.PersonalInfoRequest
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
            val email = binding.inputEmailProfile.text.toString()

            val personalInfoRequest = PersonalInfoRequest(name, surname, birthDate, email)

            registerPersonalInfo(personalInfoRequest)
        }
    }

    private fun registerPersonalInfo(request: PersonalInfoRequest) {
        apiInterface.registerPersonalInfo(request, request.email).enqueue(object : Callback<Void> {
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