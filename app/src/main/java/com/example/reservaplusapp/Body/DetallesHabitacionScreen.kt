package com.example.reservaplusapp.Body

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.reservaplusapp.Clases.Habitaciones
import com.example.reservaplusapp.Models.DetallesHabitacionViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter



import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.filled.*

import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.reservaplusapp.Clases.HabitacionDetalleResponse

@Composable
fun DetallesHabitacionScreen(
    habitacionId: Int,
    startDate: LocalDate,
    endDate: LocalDate,
    navController: NavController
) {
    val viewModel = remember { DetallesHabitacionViewModel() }
    val primaryColor = Color(0xFF57BDD3)

    LaunchedEffect(habitacionId, startDate, endDate) {
        viewModel.fetchHabitacionDetalle(
            habitacionId = habitacionId,
            startDate = startDate.toString(),
            endDate = endDate.toString()
        )
    }

    val habitacionDetalleResponse = viewModel.habitacionDetalleResponse
    val isLoading = viewModel.isLoading

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedBackground(primaryColor)

        AnimatedVisibility(
            visible = !isLoading,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    habitacionDetalleResponse == null -> {
                        ErrorMessage(habitacionId)
                    }
                    else -> {
                        DetallesHabitacionContent(
                            habitacionDetalleResponse = habitacionDetalleResponse,
                            startDate = startDate,
                            endDate = endDate,
                            navController = navController,
                            primaryColor = primaryColor
                        )
                    }
                }

                // Botón de regresar
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .padding(16.dp)
                        .background(primaryColor, CircleShape)
                        .align(Alignment.TopStart)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Regresar",
                        tint = Color.White
                    )
                }
            }
        }

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = primaryColor
            )
        }
    }
}

@Composable
fun ErrorMessage(habitacionId: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Clear,
            contentDescription = "Error",
            tint = Color.Red,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No se encontró la habitación (ID: $habitacionId)",
            color = Color.Gray,
            textAlign = TextAlign.Center,
            fontSize = 18.sp
        )
    }
}

@Composable
fun DetallesHabitacionContent(
    habitacionDetalleResponse: HabitacionDetalleResponse,
    startDate: LocalDate,
    endDate: LocalDate,
    navController: NavController,
    primaryColor: Color
) {
    val habitacion = habitacionDetalleResponse.habitacion
    val detalle = habitacionDetalleResponse.detalle
    val numero = detalle?.Numero_de_habitacion

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Imagen principal
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {
            AsyncImage(
                model = habitacion.slug,
                contentDescription = "Imagen de la habitación",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
            )
            Text(
                text = habitacion.nombre,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            )
        }

        // Información de la habitación
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),

            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Cupo",
                        tint = primaryColor
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Cupo: ${habitacion.cupo} personas",
                        fontSize = 18.sp,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                detalle?.let {
                    Text(
                        text = "Detalles de la habitación",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryColor
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    DetalleItem(Icons.Default.Home, "Número de habitación: ${it.Numero_de_habitacion}", primaryColor)
                    DetalleItem(Icons.Default.LocationOn, "Ubicación: ${it.ubicacion}", primaryColor)
                    DetalleItem(Icons.Default.Check, "Ventanas: ${it.ventanas}", primaryColor)
                    DetalleItem(Icons.Default.Menu, "Camas: ${it.camas}", primaryColor)
                    DetalleItem(Icons.Default.Info, "Número de camas: ${it.numero_de_camas}", primaryColor)
                    DetalleItem(Icons.Default.Info, "Aire acondicionado: ${if (it.aire_acondicionado) "Sí" else "No"}", primaryColor)
                    DetalleItem(Icons.Default.Info, "Jacuzzi: ${if (it.jacuzzi) "Sí" else "No"}", primaryColor)
                    DetalleItem(Icons.Default.Lock, "Disponibilidad: ${it.disponibilidad}", primaryColor)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botón de reservar
        Button(
            onClick = {
                navController.navigate("servicios/${habitacion.id}/${numero}/${startDate}/${endDate}")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(primaryColor),
            shape = RoundedCornerShape(28.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Reservar",
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Reservar ahora",
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun DetalleItem(icon: ImageVector, text: String, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, fontSize = 16.sp)
    }
}


@Composable
fun AnimatedBackground(primaryColor: Color) {
    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(60000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        rotate(rotation) {
            drawCircle(
                color = primaryColor.copy(alpha = 0.1f),
                center = Offset(canvasWidth * 0.7f, canvasHeight * 0.3f),
                radius = canvasWidth * 0.5f
            )
        }

        rotate(-rotation) {
            drawCircle(
                color = primaryColor.copy(alpha = 0.05f),
                center = Offset(canvasWidth * 0.3f, canvasHeight * 0.7f),
                radius = canvasWidth * 0.4f
            )
        }
    }
}




