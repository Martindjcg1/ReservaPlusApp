package com.example.reservaplusapp.ui.theme
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.reservaplusapp.Background.BackgroundCanvas
import com.example.reservaplusapp.Body.LoginBody
import com.example.reservaplusapp.Footer.LoginFooter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.reservaplusapp.Header.LoginHeader
import com.example.reservaplusapp.Clases.LoginRequest
import com.example.reservaplusapp.Clases.LoginResponse
import com.example.reservaplusapp.Apis.RetrofitInstance

@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    var errorMessage by remember { mutableStateOf<String?>(null) }
    Box(modifier = Modifier.fillMaxSize()) {
        BackgroundCanvas()
        LoginHeader(Modifier.align(Alignment.TopEnd))
        LoginBody(
            modifier = Modifier.align(Alignment.Center),
            onLoginClick = { username, password ->
                // Inicializar RetrofitInstance
                RetrofitInstance.initialize(context)

                // Crear el objeto LoginRequest
                val loginRequest = LoginRequest(username, password)
                Log.d("Respuesta",loginRequest.toString())
                // Realizar la solicitud de login
                RetrofitInstance.api.loginUser(loginRequest).enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        Log.e("Respuesta",response.body().toString())
                        if (response.isSuccessful) {
                            val token = response.body()?.token
                            Log.e("Login token:", token.toString())
                            if (token != null) {
                                // Guardar el token en SharedPreferences
                                val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                                sharedPreferences.edit().putString("auth_token", token).apply()

                                // Navegar a la pantalla principal
                                navController.navigate("main") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        } else {
                            errorMessage = "Credenciales inválidas"
                            Log.e("Login", "Error: ${response.errorBody()?.string()}")
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        errorMessage = "Error en la conexión: ${t.message}"
                        Log.e("Login", "Error en la conexión: ${t.message}")
                    }
                })
            }
        )
        errorMessage?.let { message ->
            Text(
                text = message,
                color = Color.Red,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 300.dp) // Ajustar posición debajo del login
            )
        }
        LoginFooter(
            Modifier.align(Alignment.BottomCenter),
            onNavigateToRegister = {
                navController.navigate("register")
            }
        )
    }
}