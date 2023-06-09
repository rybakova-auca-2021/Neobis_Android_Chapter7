package com.example.loginandsignup.model

data class NewPasswordRequest (
    val password: String,
    val password_repeat: String,
    val token: String,
    val uidb64: String
    )