package com.example.loginandsignup.view.authorization

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.loginandsignup.R
import com.example.loginandsignup.api.ApiInterface
import com.example.loginandsignup.api.RetrofitInstance
import com.example.loginandsignup.databinding.FragmentLoginBinding
import com.example.loginandsignup.model.LoginRequest
import com.example.loginandsignup.response.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val apiInterface: ApiInterface = RetrofitInstance.api

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigation()
        changeButtonColor()
    }

    private fun navigation() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailButton.text.toString()
            val password = binding.passwordButton.text.toString()
            login(email, password)
        }
        binding.forgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment2_to_resetSendToEmailFragment)
        }
        binding.isPasswordVisible.setOnClickListener {
            togglePasswordVisibility()
        }
    }

    private fun changeButtonColor() {
        binding.emailButton.addTextChangedListener { text ->
            val email = text?.toString() ?: ""
            val password = binding.passwordButton.text?.toString() ?: ""
            val isValidEmail = isValidEmail(email)
            val isValidPassword = isValidPassword(password)
            val buttonColor = if (isValidEmail && isValidPassword) R.color.invalidButtonColor else R.color.validButtonColor
            binding.loginButton.setBackgroundColor(requireContext().getColor(buttonColor))
        }

        binding.passwordButton.addTextChangedListener { text ->
            val email = binding.emailButton.text?.toString() ?: ""
            val password = text?.toString() ?: ""
            val isValidEmail = isValidEmail(email)
            val isValidPassword = isValidPassword(password)
            val buttonColor = if (isValidEmail && isValidPassword) R.color.invalidButtonColor else R.color.validButtonColor
            binding.loginButton.setBackgroundColor(requireContext().getColor(buttonColor))
        }
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }
    private fun isValidEmail(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    private fun togglePasswordVisibility() {
        val isPasswordVisible = binding.passwordButton.transformationMethod == HideReturnsTransformationMethod.getInstance()
        val newTransformationMethod = if (isPasswordVisible) PasswordTransformationMethod.getInstance() else HideReturnsTransformationMethod.getInstance()
        binding.passwordButton.transformationMethod = newTransformationMethod
        binding.passwordButton.setSelection(binding.passwordButton.text?.length ?: 0)
    }

    private fun login(email: String, password: String) {
        val request = LoginRequest(email, password)
        apiInterface.login(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null) {
                        val tokens = loginResponse.tokens
                    }
                    findNavController().navigate(R.id.action_loginFragment2_to_profileFragment)
                } else {
                    emailOrPasswordIsNotRegistered()
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Login failed. Please try again.", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun emailOrPasswordIsNotRegistered() {
        binding.errorMessage.text = getString(R.string.incorrect_login)
        binding.errorMessage.visibility = View.VISIBLE
        binding.emailButton.setTextColor(ColorStateList.valueOf(Color.RED));
    }
}