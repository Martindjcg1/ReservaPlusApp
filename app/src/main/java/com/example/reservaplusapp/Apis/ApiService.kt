package com.example.reservaplusapp.Apis

import com.example.reservaplusapp.Clases.FechasReserva
import com.example.reservaplusapp.Clases.FechasResponse
import com.example.reservaplusapp.Clases.HabitacionDetalleResponse
import com.example.reservaplusapp.Clases.HabitacionesResponse
import com.example.reservaplusapp.Clases.LoginRequest
import com.example.reservaplusapp.Clases.LoginResponse
import com.example.reservaplusapp.Clases.PasswordChangeRequest
import com.example.reservaplusapp.Clases.PasswordChangeResponse
import com.example.reservaplusapp.Clases.RegisterRequest
import com.example.reservaplusapp.Clases.RegisterResponse
import com.example.reservaplusapp.Clases.ReservasResponse
import com.example.reservaplusapp.Clases.Servicio
import com.example.reservaplusapp.Clases.UpdateProfileRequest
import com.example.reservaplusapp.Clases.UpdateProfileResponse
import com.example.reservaplusapp.Clases.UserProfileResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("Usuarios/api/registrar/") // Cambia el endpoint según tu configuración en Django
    fun registerUser(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("Usuarios/api/login/")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("Usuarios/api/logout/")
    suspend fun logout(): Response<Unit>

    @GET("HotelApp/api/servicios/")
    fun getServicios(): Call<ResponseBody>


    @GET("HotelApp/api/servicios/")
    suspend fun getServicios2(): List<Servicio>



    @GET("Usuarios/api/profile/") // Cambia la ruta si es diferente
    suspend fun getUserProfile(): UserProfileResponse


    @PUT("Usuarios/api/update-profile/")
    suspend fun updateUserProfile(
        @Body request: UpdateProfileRequest
    ): Response<UpdateProfileResponse>

    @PUT("Usuarios/api/change-password/")
    fun changePassword(@Body request: PasswordChangeRequest): Call<PasswordChangeResponse>

    @GET("Reservas/api/reservas_usuario/") // Reemplaza con la ruta correcta
    suspend fun getReservas(): Response<ReservasResponse>

    @POST("Reservas/api/validar-fechas/")
    suspend fun validarFechas(@Body fechas: FechasReserva): Response<FechasResponse>


    @GET("HotelApp/api/lista-habitaciones-disponibles/")
    suspend fun getHabitacionesDisponibles(
        @Query("fecha_inicio") fechaInicio: String,
        @Query("fecha_final") fechaFinal: String
    ): HabitacionesResponse

    @GET("HotelApp/api/detalle-habitacion/{habitacion_id}/")
    fun getDetalleHabitacion(
        @Path("habitacion_id") habitacionId: Int,
        @Query("fecha_inicio") startDate: String,
        @Query("fecha_final") endDate: String
    ): Call<HabitacionDetalleResponse>
}