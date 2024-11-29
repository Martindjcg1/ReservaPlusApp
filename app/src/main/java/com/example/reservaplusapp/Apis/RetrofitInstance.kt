package com.example.reservaplusapp.Apis

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
/*
object RetrofitInstance {
    private const val BASE_URL = "http://10.0.2.2:8000/" // Cambia por tu URL base

    // Instancia de Retrofit
    val api: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // URL base
            .addConverterFactory(GsonConverterFactory.create()) // Convertidor JSON
            .build()
    }
}*/

object RetrofitInstance {
    private const val BASE_URL = "http://10.0.2.2:8000/"

    private lateinit var appContext: Context

    fun initialize(context: Context) {
        appContext = context.applicationContext
    }

    private val client = OkHttpClient.Builder().addInterceptor { chain ->
        val sharedPreferences = appContext.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("auth_token", null)

        val request = if (token != null) {
            chain.request().newBuilder()
                .addHeader("Authorization", "Token $token")
                .build()
        } else {
            chain.request()
        }

        chain.proceed(request)
    }.build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: ApiService = retrofit.create(ApiService::class.java)
}
