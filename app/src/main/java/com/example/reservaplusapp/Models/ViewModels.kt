package com.example.reservaplusapp.Models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.reservaplusapp.Apis.RetrofitInstance
import com.example.reservaplusapp.Clases.Habitacion1
import com.example.reservaplusapp.Clases.HabitacionDetalleResponse
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

