package com.example.reservaplusapp.Header

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun LoginHeader(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    IconButton(
        onClick = { (context as? android.app.Activity)?.finish() },
        modifier = modifier.padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Cerrar aplicación",
            tint = Color.White,
            modifier = Modifier.size(32.dp)
        )
    }
}