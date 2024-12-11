package com.example.reservaplusapp

import android.app.Activity
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.reservaplusapp.ui.theme.LoginScreen
import com.example.reservaplusapp.ui.theme.RegisterScreen
import com.example.reservaplusapp.ui.theme.SplashScreen


import com.example.reservaplusapp.ui.theme.MainScreen
import com.example.reservaplusapp.ui.theme.PaymentResultSplashScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    val context = LocalContext.current
    val intent = (context as Activity).intent
    val deepLink = intent.data?.path

    // Si el deep link es igual a "/main", navegamos a la pantalla principal
    if (deepLink == "/main") {
        LaunchedEffect(key1 = deepLink) {
            navController.navigate("main") {
                popUpTo("splash") { inclusive = true }  // Elimina la pila anterior
            }
        }
    }

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(onSplashFinished = {
                navController.navigate("login") {
                    popUpTo("splash") { inclusive = true }
                }
            })
        }
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("register") {
            RegisterScreen(navController = navController)
        }
        composable("main") {
            MainScreen(navController = navController)
        }
        composable("payment_success") {
            PaymentResultSplashScreen(
                isSuccess = true,
                onSplashFinished = {
                    navController.navigate("main") {
                        popUpTo("main") { inclusive = true }
                    }
                }
            )
        }
        composable("payment_error") {
            PaymentResultSplashScreen(
                isSuccess = false,
                onSplashFinished = {
                    navController.navigate("main") {
                        popUpTo("main") { inclusive = true }
                    }
                }
            )
        }
    }
}