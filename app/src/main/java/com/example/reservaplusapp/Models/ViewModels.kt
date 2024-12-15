package com.example.reservaplusapp.Models

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reservaplusapp.Apis.RetrofitInstance
import com.example.reservaplusapp.Clases.CheckoutRequest
import com.example.reservaplusapp.Clases.FechasReserva
import com.example.reservaplusapp.Clases.FechasResponse
import com.example.reservaplusapp.Clases.Habitacion1
import com.example.reservaplusapp.Clases.HabitacionDetalleResponse
import com.example.reservaplusapp.Clases.Habitaciones
import com.example.reservaplusapp.Clases.ReservaInfo
import com.example.reservaplusapp.Clases.Servicio
import com.example.reservaplusapp.Clases.UpdateProfileRequest
import com.example.reservaplusapp.Clases.UpdateProfileResponse
import com.example.reservaplusapp.Clases.UserProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class FechasViewModel : ViewModel() {
    var fechasResponse by mutableStateOf<FechasResponse?>(null)
        private set
    var isLoading by mutableStateOf(false)
        private set

    fun validarFechas(fechasReserva: FechasReserva) {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = RetrofitInstance.api.validarFechas(fechasReserva)
                if (response.isSuccessful) {
                    Log.d("Respuesta",response.body().toString())
                    fechasResponse = response.body()
                } else {
                    fechasResponse = null
                }
            } catch (e: Exception) {
                fechasResponse = null
            } finally {
                isLoading = false
            }
        }
    }
}


class HabitacionesViewModel : ViewModel() {
    private val _habitacionesResponse = mutableStateOf<List<Habitaciones>?>(null)
    val habitacionesResponse: State<List<Habitaciones>?> = _habitacionesResponse

    private val _selectedHabitacion = mutableStateOf<Habitaciones?>(null)
    val selectedHabitacion: State<Habitaciones?> = _selectedHabitacion

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchHabitaciones(startDate: LocalDate, endDate: LocalDate) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val formatter = DateTimeFormatter.ISO_LOCAL_DATE
                val formattedStartDate = startDate.format(formatter)
                val formattedEndDate = endDate.format(formatter)
                Log.d("Fecha de inicio", formattedStartDate)
                Log.d("Fecha de terminacion", formattedEndDate)

                val response = RetrofitInstance.api.getHabitacionesDisponibles(
                    formattedStartDate,
                    formattedEndDate
                )

                _habitacionesResponse.value = response.habitacionesDisponibles
            } catch (e: Exception) {
                Log.e("HabitacionesViewModel", "Error fetching habitaciones", e)
                _habitacionesResponse.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun setSelectedHabitacion(habitacionId: Int) {
        _selectedHabitacion.value = _habitacionesResponse.value?.find { it.id == habitacionId }
        Log.d("HabitacionesViewModel", "Selected habitacion: ${_selectedHabitacion.value}")
    }
}


//detalle habitacion de reservas
class DetallesHabitacionViewModel : ViewModel() {
    var habitacionDetalleResponse  by mutableStateOf<HabitacionDetalleResponse?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun fetchHabitacionDetalle(
        habitacionId: Int,
        startDate: String,
        endDate: String
    ) {
        isLoading = true
        RetrofitInstance.api.getDetalleHabitacion(habitacionId, startDate, endDate)
            .enqueue(object : Callback<HabitacionDetalleResponse> {
                override fun onResponse(
                    call: Call<HabitacionDetalleResponse>,
                    response: Response<HabitacionDetalleResponse>
                ) {
                    if (response.isSuccessful) {
                        habitacionDetalleResponse = response.body()
                    } else {
                        habitacionDetalleResponse = null
                    }
                    isLoading = false
                }

                override fun onFailure(call: Call<HabitacionDetalleResponse>, t: Throwable) {
                    habitacionDetalleResponse  = null
                    isLoading = false
                }
            })
    }
}

//Seleccion de servicios para la reserva
class ServiciosViewModel2 : ViewModel() {
    private val _servicios = mutableStateOf<List<Servicio>>(emptyList())
    val servicios: List<Servicio> get() = _servicios.value

    private val _selectedServicios = mutableStateOf<Set<Int>>(emptySet())
    val selectedServicios: Set<Int> get() = _selectedServicios.value

    init {
        fetchServiciosFromApi()
    }

    private fun fetchServiciosFromApi() {
        viewModelScope.launch {
            try {
                val apiResponse = RetrofitInstance.api.getServicios2()
                _servicios.value = apiResponse
            } catch (e: Exception) {
                Log.e("ServiciosViewModel", "Error fetching services: ${e.message}")
            }
        }
    }

    fun toggleServicioSelection(servicioId: Int) {
        _selectedServicios.value = if (_selectedServicios.value.contains(servicioId)) {
            _selectedServicios.value - servicioId
        } else {
            _selectedServicios.value + servicioId
        }
    }
}

//Para stripe
class HabitacionesReservasViewModel : ViewModel() {
    suspend fun crearCheckout(
        habitacionId: Int,
        numeroHabitacion: Int,
        fechaInicio: String,
        fechaFinal: String,
        numeroPersonas: Int,
        servicios: List<Int>
    ): String? {
        return try {
            val requestBody = CheckoutRequest(
                fecha_inicio = fechaInicio,
                fecha_final = fechaFinal,
                numero_personas = numeroPersonas,
                servicios = servicios
            )
            Log.d("CheckoutRequest", requestBody.toString())
            val response = RetrofitInstance.api.crearCheckoutSession(
                habitacionId,
                numeroHabitacion,
                requestBody
            )
            if (response.isSuccessful) {
                response.body()?.get("url") // Retornar la URL de Stripe si tiene éxito
            } else {
                Log.e("CheckoutError", response.errorBody()?.string() ?: "Error desconocido")
                null
            }
        } catch (e: Exception) {
            Log.e("CheckoutError", "Error: ${e.message}")
            null
        }
    }
}

//Vista para tus reservas
class ReservasViewModel : ViewModel() {
    var reservasList = mutableStateOf<List<ReservaInfo>>(emptyList())
        private set
    var isLoading = mutableStateOf(false)
        private set

    fun fetchReservas() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val response = RetrofitInstance.api.getReservas()
                if (response.isSuccessful) {
                    reservasList.value = response.body()?.reservas_info ?: emptyList()
                } else {
                    Log.e("ReservasViewModel", "Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("ReservasViewModel", "Exception: ${e.message}")
            } finally {
                isLoading.value = false
            }
        }
    }

    fun cancelarReserva(reservaId: Int, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val response = RetrofitInstance.api.cancelarReserva(reservaId)
                Log.d("id de reservas", reservaId.toString())
                Log.d("respuesta", response.body().toString())
                if (response.isSuccessful) {
                    onResult(true, "Reserva cancelada exitosamente.")
                    fetchReservas() // Actualiza la lista después de cancelar
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Error desconocido"
                    onResult(false, errorMessage)
                }
            } catch (e: Exception) {
                onResult(false, e.message ?: "Error desconocido")
            } finally {
                isLoading.value = false
            }
        }
    }
}


class LogoutViewModel : ViewModel() {
    fun logout(context: Context, onLogout: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.logout()
                if (response.isSuccessful) {
                    // Borra el token de SharedPreferences
                    val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                    sharedPreferences.edit().clear().apply()
                    Log.e("Borrado",sharedPreferences.toString())
                    // Navega fuera del perfil
                    withContext(Dispatchers.Main) {
                        onLogout()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Error al cerrar sesión.", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Error de red: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}


class UserProfileViewModel : ViewModel() {
    fun fetchUserProfile(onResult: (UserProfile?) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getUserProfile()
                val userProfile = response.user
                onResult(userProfile)
            } catch (e: Exception) {
                onResult(null)
            }
        }
    }

    fun updateUserProfile(
        request: UpdateProfileRequest,
        onSuccess: (UpdateProfileResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response: Response<UpdateProfileResponse> =
                    RetrofitInstance.api.updateUserProfile(request)
                if (response.isSuccessful) {
                    response.body()?.let { onSuccess(it) }
                } else {
                    onError("Error al actualizar el perfil: ${response.errorBody()?.string()}")
                }
            } catch (e: HttpException) {
                onError("Error al conectar con el servidor: ${e.message()}")
            } catch (e: Exception) {
                onError("Error inesperado: ${e.message}")
            }
        }
    }


}

