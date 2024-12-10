package com.example.reservaplusapp.ui.theme

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun PaymentResultSplashScreen(
    isSuccess: Boolean,
    onSplashFinished: () -> Unit
) {
    var progress by remember { mutableStateOf(0f) }
    val scale by animateFloatAsState(
        targetValue = if (progress > 0.5f) 1f else 0.5f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    val alpha by animateFloatAsState(
        targetValue = if (progress > 0.1f) 1f else 0f,
        animationSpec = tween(durationMillis = 500)
    )

    val textOffset by animateFloatAsState(
        targetValue = if (progress > 0.5f) 0f else 100f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    LaunchedEffect(key1 = true) {
        repeat(100) {
            delay(20)
            progress = (it + 1) / 100f
        }
        delay(1500)
        onSplashFinished()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Canvas background
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            // Draw background
            drawRect(if (isSuccess) Color(0xFF4CAF50) else Color(0xFFF44336))

            // Draw some decorative circles
            drawCircle(
                color = Color.White.copy(alpha = 0.1f),
                center = Offset(canvasWidth * 0.2f, canvasHeight * 0.3f),
                radius = 100f
            )
            drawCircle(
                color = Color.White.copy(alpha = 0.1f),
                center = Offset(canvasWidth * 0.8f, canvasHeight * 0.7f),
                radius = 150f
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .scale(scale)
                    .alpha(alpha)
            ) {
                if (isSuccess) {
                    SuccessAnimation(progress)
                } else {
                    ErrorAnimation(progress)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = if (isSuccess) "Pago realizado con éxito" else "Error al procesar el pago",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .alpha(alpha)
                    .offset(y = textOffset.dp)
            )
        }
    }
}

@Composable
fun SuccessAnimation(progress: Float) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 1000)
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val path = Path().apply {
            moveTo(size.width * 0.2f, size.height * 0.5f)
            lineTo(size.width * 0.45f, size.height * 0.7f)
            lineTo(size.width * 0.8f, size.height * 0.3f)
        }

        drawPath(
            path = path,
            color = Color.White,
            style = Stroke(width = 10f),
            alpha = animatedProgress
        )
    }
}

@Composable
fun ErrorAnimation(progress: Float) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 1000)
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        drawCircle(
            color = Color.White,
            radius = size.minDimension * 0.4f,
            style = Stroke(width = 10f),
            alpha = animatedProgress
        )

        val crossSize = Size(size.width * 0.4f, size.height * 0.4f)
        drawLine(
            color = Color.White,
            start = Offset(size.width * 0.3f, size.height * 0.3f),
            end = Offset(size.width * 0.7f, size.height * 0.7f),
            strokeWidth = 10f,
            alpha = animatedProgress
        )
        drawLine(
            color = Color.White,
            start = Offset(size.width * 0.7f, size.height * 0.3f),
            end = Offset(size.width * 0.3f, size.height * 0.7f),
            strokeWidth = 10f,
            alpha = animatedProgress
        )
    }
}