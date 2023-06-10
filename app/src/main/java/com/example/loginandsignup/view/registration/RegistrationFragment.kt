package com.example.loginandsignup.view.registration

import android.app.AlertDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.loginandsignup.R
import com.example.loginandsignup.Utils
import com.example.loginandsignup.Utils.email
import com.example.loginandsignup.Utils.token
import com.example.loginandsignup.api.RetrofitInstance
import com.example.loginandsignup.databinding.FragmentRegistrationBinding
import com.example.loginandsignup.model.EmailRegistrationRequest
import com.example.loginandsignup.response.EmailRegistrationResponse
import com.example.loginandsignup.response.EmailVerificationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeButtonColor()
        setupNavigation()
    }

    private fun setupNavigation() {
        binding.buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_registrationFragment_to_splashScreenFragment2)
        }
        binding.loginButton.setOnClickListener {
            val email = binding.emailButton.text.toString()
            if (isValidEmail(email)) {
                registerWithEmail(email)
            } else {
                Toast.makeText(requireContext(),"Invlalid email", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerWithEmail(email: String) {
        val request = EmailRegistrationRequest(email)
        val apiInterface = RetrofitInstance.api
        val call = apiInterface.registerWithEmail(request)

        call.enqueue(object : Callback<EmailRegistrationResponse> {
            override fun onResponse(
                call: Call<EmailRegistrationResponse>,
                response: Response<EmailRegistrationResponse>
            ) {
                if (response.isSuccessful) {
                    showCustomDialog()
                    Log.d("RegistrationFragment", "Email value: $email")
                    Utils.email = email
                } else {
                    showEmailAlreadyRegisteredError()
                }
            }
            override fun onFailure(call: Call<EmailRegistrationResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Failed. Please try again.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun isValidEmail(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    private fun changeButtonColor() {
        binding.emailButton.addTextChangedListener { text ->
            val email = text?.toString() ?: ""
            val isValidEmail = isValidEmail(email)
            val buttonColor = if (isValidEmail) R.color.invalidButtonColor else R.color.validButtonColor
            binding.loginButton.isEnabled = isValidEmail
            binding.loginButton.setBackgroundColor(requireContext().getColor(buttonColor))
        }
    }
    private fun showEmailAlreadyRegisteredError() {
        binding.emailErrorMessage.text = getString(R.string.email_already_registered)
        binding.emailErrorMessage.visibility = View.VISIBLE
        binding.emailButton.setTextColor(ColorStateList.valueOf(Color.RED))
        binding.emailButton.setBackgroundResource(R.drawable.bitton_border_red)
    }

    private fun showCustomDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_mail, null)
        val dialogButton = dialogView.findViewById<Button>(R.id.dialogButton)

        val builder = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(true)

        val alertDialog = builder.create()

        dialogButton.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.show()
    }
}