package com.example.reservaplusapp.Footer

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoginFooter(
    modifier: Modifier = Modifier,
    onNavigateToRegister: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextButton(
            onClick = onNavigateToRegister,
            colors = ButtonDefaults.textButtonColors(contentColor = Color.White)
        ) {
            Text("¿Aún no tienes una cuenta? Regístrate aquí")
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}