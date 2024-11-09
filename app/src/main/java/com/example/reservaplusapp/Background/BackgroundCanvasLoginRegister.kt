package com.example.reservaplusapp.Background

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

@Composable
fun BackgroundCanvas() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        drawRect(Color(0xFF57BDD3))
        drawCircle(
            color = Color.White.copy(alpha = 0.1f),
            center = Offset(canvasWidth * 0.1f, canvasHeight * 0.1f),
            radius = 100f
        )
        drawCircle(
            color = Color.White.copy(alpha = 0.1f),
            center = Offset(canvasWidth * 0.9f, canvasHeight * 0.9f),
            radius = 150f
        )
    }
}