package com.example.reservaplusapp
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("Usuarios/api/registrar/") // Cambia el endpoint según tu configuración en Django
    fun registerUser(@Body user: RegisterRequest): Call<RegisterResponse>
}