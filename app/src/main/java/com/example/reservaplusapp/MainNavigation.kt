package com.example.reservaplusapp

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable

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