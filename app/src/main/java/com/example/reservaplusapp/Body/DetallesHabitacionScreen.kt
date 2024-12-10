package com.example.reservaplusapp.Body

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
        when {
            isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color(0xFF57BDD3)
                )
            }
            habitacionDetalleResponse == null -> {
                ErrorMessage(habitacionId)
            }
            else -> {
                DetallesHabitacionContent(
                    habitacionDetalleResponse = habitacionDetalleResponse,
                    startDate = startDate,
                    endDate = endDate,
                    navController = navController
                )
            }
        }

        // Botón de regresar
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .padding(16.dp)
                .background(Color(0xFF57BDD3), CircleShape)
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
            imageVector = Icons.Default.Delete,
            contentDescription = "Error",
            tint = Color.Red,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No se encontró la habitación (ID: $habitacionId)",
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun DetallesHabitacionContent(
    habitacionDetalleResponse: HabitacionDetalleResponse,
    startDate: LocalDate,
    endDate: LocalDate,
    navController: NavController
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
        AsyncImage(
            model = habitacion.slug,
            contentDescription = "Imagen de la habitación",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)),
            contentScale = ContentScale.Crop
        )

        // Información de la habitación
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = habitacion.nombre,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF57BDD3)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Cupo",
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Cupo: ${habitacion.cupo} personas",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            detalle?.let {
                Text(
                    text = "Detalles de la habitación",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF57BDD3)
                )
                Spacer(modifier = Modifier.height(8.dp))
                DetalleItem(Icons.Default.Home, "Número de habitación: ${it.Numero_de_habitacion}")
                DetalleItem(Icons.Default.LocationOn, "Ubicación: ${it.ubicacion}")
                DetalleItem(Icons.Default.Check, "Ventanas: ${it.ventanas}")
                DetalleItem(Icons.Default.Menu, "Camas: ${it.camas}")
                DetalleItem(Icons.Default.Info, "Número de camas: ${it.numero_de_camas}")
                DetalleItem(Icons.Default.Info, "Aire acondicionado: ${if (it.aire_acondicionado) "Sí" else "No"}")
                DetalleItem(Icons.Default.Info, "Jacuzzi: ${if (it.jacuzzi) "Sí" else "No"}")
                DetalleItem(Icons.Default.Lock, "Disponibilidad: ${it.disponibilidad}")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botón de reservar
            Button(
                onClick = {
                    navController.navigate("servicios/${habitacion.id}/${numero}/${startDate}/${endDate}")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF57BDD3)),
                shape = RoundedCornerShape(8.dp)
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
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun DetalleItem(icon: ImageVector, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF57BDD3),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, fontSize = 16.sp)
    }
}
