package com.example.loginandsignup.view.createPassword

import android.graphics.Color
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.loginandsignup.R
import com.example.loginandsignup.api.ApiInterface
import com.example.loginandsignup.api.RetrofitInstance
import com.example.loginandsignup.databinding.FragmentCreatePasswordBinding
import com.example.loginandsignup.model.PasswordRegistrationRequest
import com.example.loginandsignup.response.PasswordRegistrationResponse
import com.example.loginandsignup.viewModel.RegistrationViewModel
//import com.example.loginandsignup.model.PasswordRegistrationRequest
//import com.example.loginandsignup.response.PasswordRegistrationResponse
//import com.example.loginandsignup.viewModel.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreatePasswordFragment : Fragment() {
    private lateinit var binding: FragmentCreatePasswordBinding
    private val apiInterface: ApiInterface = RetrofitInstance.api
    private val viewModel: RegistrationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreatePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigation()
        setupPasswordValidation()
    }

    private fun navigation() {
        binding.buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_createPasswordFragment2_to_additionalInfoFragment2)
        }
        binding.loginButton2.setOnClickListener {
            registerPassword()
            findNavController().navigate(R.id.action_createPasswordFragment2_to_loginFragment2)
        }
        binding.isPasswordVisible.setOnClickListener {
            togglePasswordVisibilityCreate()
        }
        binding.isPasswordVisible2.setOnClickListener {
            togglePasswordVisibilityRepeat()
        }
    }

    private fun setupPasswordValidation() {
        binding.createPassword.addTextChangedListener {
            val password = it.toString()

            val hasUpperCase = password.matches(".*[A-Z].*".toRegex())
            val hasNumber = password.matches(".*\\d.*".toRegex())
            val hasSpecialSymbol = password.matches(".*[!@#$%^&*(),.?\":{}|<>].*".toRegex())
            val isPasswordMatch = password == binding.repeatPassword.text.toString()

            val validColor = ContextCompat.getColor(requireContext(), R.color.validColor)

            binding.isLetterBig.setTextColor(if (hasUpperCase) validColor else Color.RED)
            binding.pointBigLetter.setColorFilter(if (hasUpperCase) validColor else Color.RED)

            binding.areThereNumbers.setTextColor(if (hasNumber) validColor else Color.RED)
            binding.pointNumbers.setColorFilter(if (hasNumber) validColor else Color.RED)

            binding.specialSymbols.setTextColor(if (hasSpecialSymbol) validColor else Color.RED)
            binding.pointSymbols.setColorFilter(if (hasSpecialSymbol) validColor else Color.RED)

            binding.isUniquePassword.setTextColor(if (isPasswordMatch) validColor else Color.RED)
            binding.pointPassword.setColorFilter(if (isPasswordMatch) validColor else Color.RED)

            val isValid = hasUpperCase && hasNumber && hasSpecialSymbol && isPasswordMatch

            binding.loginButton2.isEnabled = isValid

            // Change button color based on validity
            val buttonColor = if (isValid) R.color.invalidButtonColor else R.color.validButtonColor
            val buttonColorRes = ContextCompat.getColor(requireContext(), buttonColor)
            binding.loginButton2.setBackgroundColor(buttonColorRes)
        }
    }
    private fun togglePasswordVisibilityCreate() {
        val isPasswordVisible = binding.createPassword.transformationMethod == HideReturnsTransformationMethod.getInstance()
        val newTransformationMethod = if (isPasswordVisible) PasswordTransformationMethod.getInstance() else HideReturnsTransformationMethod.getInstance()
        binding.createPassword.transformationMethod = newTransformationMethod
        binding.createPassword.setSelection(binding.createPassword.text?.length ?: 0)
    }
    private fun togglePasswordVisibilityRepeat() {
        val isPasswordVisible = binding.repeatPassword.transformationMethod == HideReturnsTransformationMethod.getInstance()
        val newTransformationMethod = if (isPasswordVisible) PasswordTransformationMethod.getInstance() else HideReturnsTransformationMethod.getInstance()
        binding.repeatPassword.transformationMethod = newTransformationMethod
        binding.repeatPassword.setSelection(binding.repeatPassword.text?.length ?: 0)
    }

    private fun registerPassword() {
        val password = binding.createPassword.text?.toString() ?: ""
        val passwordRepeat = binding.repeatPassword.text?.toString() ?: ""
        val request = PasswordRegistrationRequest(password, passwordRepeat)
        val email: String = viewModel.email

        val call = email.let { apiInterface.registerPassword(it, request) }
        call.enqueue(object : Callback<PasswordRegistrationResponse> {
            override fun onResponse(
                call: Call<PasswordRegistrationResponse>,
                response: Response<PasswordRegistrationResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Пароль сохранен", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Попробуйте еще раз", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PasswordRegistrationResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Пароль не зарегистрирован", Toast.LENGTH_SHORT).show()
            }
        })
    }
}