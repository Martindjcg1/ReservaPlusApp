package com.example.reservaplusapp.Models

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reservaplusapp.Apis.RetrofitInstance
import com.example.reservaplusapp.Clases.Habitacion1
import com.example.reservaplusapp.Clases.HabitacionDetalleResponse
import com.example.reservaplusapp.Clases.Servicio
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

