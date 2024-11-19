package com.example.reservaplusapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "http://10.0.2.2:8000/" // Cambia por tu URL base

    // Instancia de Retrofit
    val api: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // URL base
            .addConverterFactory(GsonConverterFactory.create()) // Convertidor JSON
            .build()
    }
}
