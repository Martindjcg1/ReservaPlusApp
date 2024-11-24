package com.example.reservaplusapp
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.POST

interface ApiService {
    @POST("Usuarios/api/registrar/") // Cambia el endpoint según tu configuración en Django
    fun registerUser(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("Usuarios/api/login/")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>
}