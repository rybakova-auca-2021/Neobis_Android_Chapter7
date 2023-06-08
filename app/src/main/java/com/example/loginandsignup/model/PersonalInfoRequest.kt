package com.example.loginandsignup.model

data class PersonalInfoRequest(
    val first_name: String,
    val last_name: String,
    val birth_date: String,
    var email: String
)
