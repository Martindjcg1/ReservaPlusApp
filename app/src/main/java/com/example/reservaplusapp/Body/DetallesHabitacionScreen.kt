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
        // Botón de regresar
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .padding(16.dp)
                .background(
                    color = Color.Black.copy(alpha = 0.4f),
                    shape = CircleShape
                )
                .align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Regresar",
                tint = Color.White
            )
        }

        when {
            isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color(0xFF57BDD3)
                )
            }
            habitacionDetalleResponse == null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No se encontró la habitación (ID: $habitacionId)",
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            }
            else -> {
                val habitacion = habitacionDetalleResponse.habitacion
                val detalle = habitacionDetalleResponse.detalle
                val numero= detalle?.Numero_de_habitacion

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    // Imagen principal
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    ) {
                        AsyncImage(
                            model = habitacion.slug,
                            contentDescription = "Imagen de la habitación",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    // Información de la habitación
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = habitacion.nombre,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Cupo: ${habitacion.cupo} personas",
                            fontSize = 16.sp,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        detalle?.let {
                            Text(
                                text = "Detalles de la habitación:",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Número de habitación: ${it.Numero_de_habitacion}")
                            Text(text = "Ubicación: ${it.ubicacion}")
                            Text(text = "Ventanas: ${it.ventanas}")
                            Text(text = "Camas: ${it.camas}")
                            Text(text = "Número de camas: ${it.numero_de_camas}")
                            Text(text = "Aire acondicionado: ${if (it.aire_acondicionado) "Sí" else "No"}")
                            Text(text = "Jacuzzi: ${if (it.jacuzzi) "Sí" else "No"}")
                            Text(text = "Disponibilidad: ${it.disponibilidad}")
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Botón de reservar
                        Button(
                            onClick = {
                                navController.navigate("formulario/${habitacionId}/${numero}/${startDate}/${endDate}")
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF57BDD3)
                            )
                        ) {
                            Text(
                                text = "Reservar",
                                fontSize = 18.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}
