package com.example.reservaplusapp.Body

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.reservaplusapp.Apis.ApiService
import com.example.reservaplusapp.Clases.Habitaciones
import kotlinx.coroutines.launch
import java.time.LocalDate
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import com.example.reservaplusapp.Apis.RetrofitInstance
import com.example.reservaplusapp.Models.HabitacionesViewModel
import java.time.format.DateTimeFormatter





@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HabitacionesScreen(
    startDate: LocalDate,
    endDate: LocalDate,
    navController: NavController,
    viewModel: HabitacionesViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    modifier: Modifier = Modifier
) {
    val habitaciones by viewModel.habitacionesResponse
    val isLoading by viewModel.isLoading

    LaunchedEffect(Unit) {
        viewModel.fetchHabitaciones(startDate, endDate)
    }

    Box(modifier = modifier.fillMaxSize()) {
        when {
            isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color(0xFF57BDD3)
                )
            }
            habitaciones.isNullOrEmpty() -> {
                Text(
                    text = "No hay habitaciones disponibles.",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .padding(bottom = 80.dp), // Add extra padding at the bottom
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Text(
                            text = "Habitaciones Disponibles",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF57BDD3),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    items(habitaciones!!) { habitacion ->
                        HabitacionCard(
                            habitacion = habitacion,
                            startDate = startDate,
                            endDate = endDate,
                            onVerDetalles = {
                                navController.navigate("detalle/${habitacion.id}/$startDate/$endDate")
                            }
                        )
                    }

                    // Add a spacer at the end of the list
                    item {
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun HabitacionCard(
    habitacion: Habitaciones,
    startDate: LocalDate,
    endDate: LocalDate,
    onVerDetalles: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AsyncImage(
                model = habitacion.slug,
                contentDescription = "Imagen de la habitación",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = habitacion.nombre,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Text(
                text = "Precio: $${habitacion.precio}",
                fontSize = 16.sp,
                color = Color.Gray
            )

            Text(
                text = "Cupo: ${habitacion.cupo} personas",
                fontSize = 16.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onVerDetalles,
                modifier = Modifier.align(Alignment.End),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF57BDD3)
                )
            ) {
                Text(
                    text = "Ver Detalles",
                    color = Color.White
                )
            }
        }
    }
}



