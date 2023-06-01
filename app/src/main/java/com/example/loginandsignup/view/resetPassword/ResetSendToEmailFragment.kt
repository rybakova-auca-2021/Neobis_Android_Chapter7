package com.example.loginandsignup.view.resetPassword

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.loginandsignup.R
import com.example.loginandsignup.databinding.FragmentResetSendToEmailBinding

class ResetSendToEmailFragment : Fragment() {
    private lateinit var binding: FragmentResetSendToEmailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResetSendToEmailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigation()
        changeButtonColor()
    }

    private fun navigation() {
        binding.buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_resetSendToEmailFragment_to_loginFragment2)
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

        dialogButton.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.show()
    }
}