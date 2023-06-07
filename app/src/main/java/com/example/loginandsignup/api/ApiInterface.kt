package com.example.loginandsignup.api

import com.example.loginandsignup.model.*
import com.example.loginandsignup.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    // User Registration - Email
    @POST("/auth/register/email/")
    fun registerWithEmail(@Body request: EmailRegistrationRequest): Call<EmailRegistrationResponse>

    // Email Verification
    @GET("/auth/verify-email/")
    fun verifyEmail(@Query("token") token: String): Call<EmailVerificationResponse>

    // User Registration - Personal Information
    @PUT("/auth/register/personal-info/")
    fun registerPersonalInfo(@Body request: PersonalInfoRequest): Call<Void>

    // User Registration - Password
    @PUT("/auth/register/password/")
    fun registerPassword(
        @Query("email") email: String,
        @Body request: PasswordRegistrationRequest
    ): Call<PasswordRegistrationResponse>

//     User Login
    @POST("/auth/login/")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    // Request Password Reset Email
    @POST("/auth/request-password-reset/")
    fun requestPasswordResetEmail(@Body request: PasswordResetEmailRequest): Call<PasswordResetEmailResponse>

    // Check Password Reset Token
    @GET("/auth/reset-password/check/{uidb64}/{token}/")
    fun checkPasswordResetToken(
        @Path("uidb64") uidb64: String,
        @Path("token") token: String
    ): Call<PasswordResetTokenResponse>

    // Set New Password
    @POST("/auth/reset-password/confirm/")
    fun setNewPassword(@Body request: NewPasswordRequest): Call<Void>
}
