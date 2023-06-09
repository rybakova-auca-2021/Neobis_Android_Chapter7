package com.example.loginandsignup.api

import com.example.loginandsignup.model.*
import com.example.loginandsignup.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    @POST("/auth/register/email/")
    fun registerWithEmail(@Body request: EmailRegistrationRequest): Call<EmailRegistrationResponse>

    @GET("/auth/verify-email/")
    fun verifyEmail(@Query("token") token: String): Call<EmailVerificationResponse>

    @PUT("/auth/register/personal-info/")
    fun registerPersonalInfo(
        @Body request: PersonalInfoRequest,
        @Query("email") email: String
    ): Call<Void>

    @PUT("http://35.234.124.146/auth/register/password/{email}/")
    fun registerPassword(
        @Path("email") email: String,
        @Body request: PasswordRegistrationRequest
    ): Call<PasswordRegistrationResponse>

    @POST("/auth/login/")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("/auth/request-reset-email/")
    fun requestPasswordResetEmail(@Body request: PasswordResetEmailRequest): Call<PasswordResetEmailResponse>

    @GET("/auth/password-reset/{uidb64}/{token}/")
    fun checkPasswordResetToken(
        @Path("uidb64") uidb64: String,
        @Path("token") token: String
    ): Call<PasswordResetTokenResponse>

    @PATCH("/auth/password-reset-complete/")
    fun setNewPassword(@Body request: NewPasswordRequest): Call<Void>
}
