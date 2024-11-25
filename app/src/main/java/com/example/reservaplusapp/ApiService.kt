package com.example.reservaplusapp
import com.example.reservaplusapp.Clases.LoginRequest
import com.example.reservaplusapp.Clases.LoginResponse
import com.example.reservaplusapp.Clases.RegisterRequest
import com.example.reservaplusapp.Clases.RegisterResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("Usuarios/api/registrar/") // Cambia el endpoint según tu configuración en Django
    fun registerUser(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("Usuarios/api/login/")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @GET("HotelApp/api/servicios/")
    fun getServicios(): Call<ResponseBody>
}