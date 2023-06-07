package com.example.loginandsignup.model

data class NewPasswordRequest (
    val password: String,
    val token: String,
    val uidb64: String
    )