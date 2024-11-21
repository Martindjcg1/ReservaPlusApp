package com.example.reservaplusapp.ui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.reservaplusapp.Background.BackgroundCanvas
import com.example.reservaplusapp.Body.LoginBody
import com.example.reservaplusapp.Footer.LoginFooter

import com.example.reservaplusapp.Header.LoginHeader

@Composable
fun LoginScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        BackgroundCanvas()
        LoginHeader(Modifier.align(Alignment.TopEnd))
        LoginBody(
            Modifier.align(Alignment.Center),
            onLoginClick = {
                // Implement your login logic here
                // For now, we'll just navigate to the main screen
                navController.navigate("main") {
                    popUpTo("login") { inclusive = true }
                }
            }
        )
        LoginFooter(
            Modifier.align(Alignment.BottomCenter),
            onNavigateToRegister = {
                navController.navigate("register")
            }
        )
    }
}