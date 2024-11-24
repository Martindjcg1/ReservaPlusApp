package com.example.reservaplusapp

data class RegisterRequest(
    val username: String,
    val password1: String,
    val password2: String,
    val first_name: String,
    val last_name: String,
    val email: String
)

data class RegisterResponse(
    val message: String,
    val user: UserResponse,
    val token: String
)

data class UserResponse(
    val username: String,
    val first_name: String,
    val last_name: String,
    val email: String
)

data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResponse(
    val token: String
)

