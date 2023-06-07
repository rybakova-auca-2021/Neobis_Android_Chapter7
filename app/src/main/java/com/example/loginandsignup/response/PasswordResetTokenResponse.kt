package com.example.loginandsignup.response

data class PasswordResetTokenResponse(
    val success: Boolean,
    val message: String,
    val uidb64: String,
    val token: String
)
