package com.example.loginandsignup.response

data class Tokens(
    val refresh: String,
    val access: String
)

data class LoginResponse(
    val email: String,
    val tokens: Tokens
)
