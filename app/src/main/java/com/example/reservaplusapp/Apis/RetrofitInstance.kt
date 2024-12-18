package com.example.reservaplusapp.Apis

import android.content.Context
import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {
    //private const val BASE_URL = "http://10.0.2.2:8000/"
    private const val BASE_URL = "https://reservaplus.onrender.com/"

    private lateinit var appContext: Context

    fun initialize(context: Context) {
        appContext = context.applicationContext
    }

    private val client = OkHttpClient.Builder().addInterceptor { chain ->
        val sharedPreferences = appContext.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        //sharedPreferences.edit().clear().apply()
        val token = sharedPreferences.getString("auth_token", null)
        Log.e("Token=", token.toString())
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
