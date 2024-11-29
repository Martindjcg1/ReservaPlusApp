package com.example.reservaplusapp.ui.theme


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.reservaplusapp.Background.BackgroundCanvas
import com.example.reservaplusapp.Body.RegisterBody
import com.example.reservaplusapp.Footer.RegisterFooter
import com.example.reservaplusapp.Header.RegisterHeader
import com.example.reservaplusapp.Clases.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import com.example.reservaplusapp.Apis.RetrofitInstance

@Composable
fun RegisterScreen(navController: NavController) {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        BackgroundCanvas()
        RegisterHeader(Modifier.align(Alignment.TopEnd))
        RegisterBody(
            Modifier.align(Alignment.Center),
            onRegisterClick = { registerRequest ->
                // Asegúrate de que RetrofitInstance esté inicializado
                RetrofitInstance.initialize(context)

                // Usamos RetrofitInstance para hacer la solicitud
                RetrofitInstance.api.registerUser(registerRequest).enqueue(object : Callback<RegisterResponse> {
                    override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                        if (response.isSuccessful) {
                            val token = response.body()?.token
                            if (token != null) {
                                // Guardar token en SharedPreferences
                                val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                                sharedPreferences.edit().putString("auth_token", token).apply()

                                // Navegar a la pantalla principal
                                navController.navigate("main") {
                                    popUpTo("register") { inclusive = true }
                                }
                            }
                        } else {
                            Log.e("Registro", "Error: ${response.errorBody()?.string()}")
                        }
                    }

                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        Log.e("Registro", "Error en la conexión: ${t.message}")
                    }
                })
            }
        )
        RegisterFooter(
            Modifier.align(Alignment.BottomCenter),
            onNavigateToLogin = {
                navController.popBackStack()
            }
        )
    }
}

