//package com.example.loginandsignup.view.resetPassword
//
//import android.graphics.Color
//import android.os.Bundle
//import android.text.method.HideReturnsTransformationMethod
//import android.text.method.PasswordTransformationMethod
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.core.content.ContextCompat
//import androidx.core.widget.addTextChangedListener
//import androidx.fragment.app.viewModels
//import androidx.navigation.fragment.findNavController
//import com.example.loginandsignup.R
//import com.example.loginandsignup.api.ApiInterface
//import com.example.loginandsignup.api.RetrofitInstance
//import com.example.loginandsignup.databinding.FragmentResetPasswordBinding
//import com.example.loginandsignup.model.NewPasswordRequest
//import com.example.loginandsignup.viewModel.LoginViewModel
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class ResetPasswordFragment : Fragment() {
//    private lateinit var binding: FragmentResetPasswordBinding
//    private val apiInterface: ApiInterface = RetrofitInstance.api
//    private val loginViewModel: LoginViewModel by viewModels()
//
//    private lateinit var token: String
//    private lateinit var uidb64: String
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        token = loginViewModel.token
//        uidb64 = loginViewModel.uidb64
//
//        navigation()
//        setupPasswordValidation()
//    }
//
//    private fun navigation() {
//        binding.buttonBack.setOnClickListener {
//            findNavController().navigate(R.id.action_resetPasswordFragment_to_resetSendToEmailFragment)
//        }
//        binding.loginButton2.setOnClickListener{
//            val password = binding.repeatPassword.text.toString()
//            setNewPassword(password)
//            //findNavController().navigate(R.id.action_resetPasswordFragment_to_loginFragment2)
//        }
//        binding.isPasswordVisible.setOnClickListener {
//            togglePasswordVisibilityCreate()
//        }
//        binding.isPasswordVisible2.setOnClickListener {
//            togglePasswordVisibilityRepeat()
//        }
//    }
//    private fun setNewPassword(password: String) {
//        val newPasswordRequest = NewPasswordRequest(password, token, uidb64)
//
//        apiInterface.setNewPassword(newPasswordRequest).enqueue(object : Callback<Void> {
//            override fun onResponse(call: Call<Void>, response: Response<Void>) {
//                if (response.isSuccessful) {
//                    findNavController().navigate(R.id.action_resetPasswordFragment_to_loginFragment2)
//                } else {
//                    Toast.makeText(requireContext(), "Failed to set a new password. Please try again.\"", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: Call<Void>, t: Throwable) {
//                // Request failed
//            }
//        })
//    }
//
//    private fun setupPasswordValidation() {
//        binding.createPassword.addTextChangedListener {
//            val password = it.toString()
//
//            val hasUpperCase = password.matches(".*[A-Z].*".toRegex())
//            val hasNumber = password.matches(".*\\d.*".toRegex())
//            val hasSpecialSymbol = password.matches(".*[!@#$%^&*(),.?\":{}|<>].*".toRegex())
//            val isPasswordMatch = password == binding.repeatPassword.text.toString()
//
//            val validColor = ContextCompat.getColor(requireContext(), R.color.validColor)
//
//            binding.isLetterBig.setTextColor(if (hasUpperCase) validColor else Color.RED)
//            binding.pointBigLetter.setColorFilter(if (hasUpperCase) validColor else Color.RED)
//
//            binding.areThereNumbers.setTextColor(if (hasNumber) validColor else Color.RED)
//            binding.pointNumbers.setColorFilter(if (hasNumber) validColor else Color.RED)
//
//            binding.specialSymbols.setTextColor(if (hasSpecialSymbol) validColor else Color.RED)
//            binding.pointSymbols.setColorFilter(if (hasSpecialSymbol) validColor else Color.RED)
//
//            binding.isUniquePassword.setTextColor(if (isPasswordMatch) validColor else Color.RED)
//            binding.pointPassword.setColorFilter(if (isPasswordMatch) validColor else Color.RED)
//
//            val isValid = hasUpperCase && hasNumber && hasSpecialSymbol && isPasswordMatch
//
//            binding.loginButton2.isEnabled = isValid
//
//            // Change button color based on validity
//            val buttonColor = if (isValid) R.color.invalidButtonColor else R.color.validButtonColor
//            val buttonColorRes = ContextCompat.getColor(requireContext(), buttonColor)
//            binding.loginButton2.setBackgroundColor(buttonColorRes)
//        }
//    }
//
//    private fun togglePasswordVisibilityCreate() {
//        val isPasswordVisible = binding.createPassword.transformationMethod == HideReturnsTransformationMethod.getInstance()
//        val newTransformationMethod = if (isPasswordVisible) PasswordTransformationMethod.getInstance() else HideReturnsTransformationMethod.getInstance()
//        binding.createPassword.transformationMethod = newTransformationMethod
//        binding.createPassword.setSelection(binding.createPassword.text?.length ?: 0)
//    }
//    private fun togglePasswordVisibilityRepeat() {
//        val isPasswordVisible = binding.repeatPassword.transformationMethod == HideReturnsTransformationMethod.getInstance()
//        val newTransformationMethod = if (isPasswordVisible) PasswordTransformationMethod.getInstance() else HideReturnsTransformationMethod.getInstance()
//        binding.repeatPassword.transformationMethod = newTransformationMethod
//        binding.repeatPassword.setSelection(binding.createPassword.text?.length ?: 0)
//    }
//}