package com.example.reservaplusapp.Clases

import com.google.gson.annotations.SerializedName

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


data class UserProfileResponse(
    val user: UserProfile // Aquí está el campo 'user' que contiene el perfil del usuario
)

// Clase que representa el perfil de usuario
data class UserProfile(
    val id: Int = 0,
    val username: String? = null,
    val email: String? = null,
    val first_name: String? = null,
    val last_name: String? = null,
    val date_joined:String?=null
)