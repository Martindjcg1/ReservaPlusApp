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


@Composable
fun RegisterScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        BackgroundCanvas()
        RegisterHeader(Modifier.align(Alignment.TopEnd))
        RegisterBody(
            Modifier.align(Alignment.Center),
            onRegisterClick = {
               //logica del registro
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

