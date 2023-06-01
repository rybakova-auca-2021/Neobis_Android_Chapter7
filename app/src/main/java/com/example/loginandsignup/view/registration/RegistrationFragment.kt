package com.example.loginandsignup.view.registration

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.loginandsignup.R
import com.example.loginandsignup.databinding.FragmentRegistrationBinding

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
        navigation()
    }

    private fun navigation() {
        binding.buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_registrationFragment_to_splashScreenFragment2)
        }
        binding.loginButton.setOnClickListener {
            showCustomDialog()
        }
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
    private fun isValidEmail(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    @SuppressLint("MissingInflatedId")
    private fun showCustomDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_mail, null)
        val dialogButton = dialogView.findViewById<Button>(R.id.dialogButton)

        val builder = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(true)

        val alertDialog = builder.create()

        // Handle dialog button click
        dialogButton.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    fun checkIfInDataBase() {
        //TODO - check if this email is already in database, if yes - make text red and create a
        // notification that this email is already registered
    }
}