package com.example.reservaplusapp.Body

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReservasContent(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    var startDate by remember { mutableStateOf<LocalDate?>(null) }
    var endDate by remember { mutableStateOf<LocalDate?>(null) }
    val calendarState = rememberSheetState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Selecciona tus Fechas de Reserva",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF57BDD3),
            modifier = Modifier.padding(vertical = 24.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Rango de Fechas:",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                Text(
                    text = if (startDate != null && endDate != null) {
                        "${dateFormatter.format(startDate)} - ${dateFormatter.format(endDate)}"
                    } else {
                        "Selecciona las fechas"
                    },
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Button(
                    onClick = { calendarState.show() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF57BDD3))
                ) {
                    Text("Seleccionar Fechas")
                }

                CalendarDialog(
                    state = calendarState,
                    config = CalendarConfig(
                        monthSelection = true,
                        yearSelection = true
                    ),
                    selection = CalendarSelection.Period { start, end ->
                        startDate = start
                        endDate = end
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    startDate = null
                    endDate = null
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFF57BDD3)
                ),
                border = BorderStroke(1.dp, Color(0xFF57BDD3))
            ) {
                Text("Limpiar")
            }

            Button(
                onClick = {
                    if (startDate != null && endDate != null) {
                        navController.navigate("habitaciones/${startDate}/${endDate}")
                    }
                },
                enabled = startDate != null && endDate != null,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF57BDD3)
                )
            ) {
                Text("Confirmar")
            }
        }
    }
}

// Modelo de datos para las habitaciones
data class HabitacionesDisponibles(
    val id: Int,
    val nombre: String,
    val ubicacion: String,
    val precio: Double,
    val imagen: String,
    val descripcion: String,
    val cupo: Int,
    val caracteristicas: Map<String, String>
)

@Composable
fun HabitacionesScreen(
    startDate: LocalDate,
    endDate: LocalDate,
    navController: NavController
) {
    val habitaciones = remember {
        listOf(
            HabitacionesDisponibles(
                id = 1,
                nombre = "Habitacion de lujosa",
                ubicacion = "San Pedro Cahro Michoacan",
                precio = 1500.00,
                imagen = "https://example.com/imagen_habitacion_lujosa.jpg",
                descripcion = "Habitación de lujo con todas las comodidades",
                cupo = 4,
                caracteristicas = mapOf(
                    "Jacuzzi" to "Sí",
                    "Ventanas" to "2",
                    "Camas" to "Camas Comodas",
                    "Aire Acondicionado" to "Sí"
                )
            ),
            HabitacionesDisponibles(
                id = 2,
                nombre = "Habitacion economica",
                ubicacion = "Jiquilpan",
                precio = 500.00,
                imagen = "https://example.com/imagen_habitacion_economica.jpg",
                descripcion = "Habitación económica con lo básico",
                cupo = 2,
                caracteristicas = mapOf(
                    "Ventanas" to "1",
                    "Camas" to "Cama Individual",
                    "Aire Acondicionado" to "No"
                )
            ),
            HabitacionesDisponibles(
                id = 3,
                nombre = "Habitacion 5 estrellas",
                ubicacion = "Jiquilpan",
                precio = 1200.00,
                imagen = "https://example.com/imagen_habitacion_5_estrellas.jpg",
                descripcion = "Habitación de lujo con vista panorámica",
                cupo = 3,
                caracteristicas = mapOf(
                    "Jacuzzi" to "Sí",
                    "Ventanas" to "3",
                    "Camas" to "Cama King Size",
                    "Aire Acondicionado" to "Sí"
                )
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "Habitaciones Disponibles",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF57BDD3),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(habitaciones) { habitacion ->
                HabitacionCard(
                    habitacion = habitacion,
                    onVerDetalles = {
                        navController.navigate(
                            "detalles/${habitacion.id}/${startDate}/${endDate}"
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun HabitacionCard(
    habitacion: HabitacionesDisponibles,
    onVerDetalles: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            AsyncImage(
                model = habitacion.imagen,
                contentDescription = habitacion.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = habitacion.nombre,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Ubicación: ${habitacion.ubicacion}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                Text(
                    text = "Precio: $${habitacion.precio}",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF57BDD3),
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                Button(
                    onClick = onVerDetalles,
                    modifier = Modifier.align(Alignment.End),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF57BDD3)
                    )
                ) {
                    Text("Ver Detalles")
                }
            }
        }
    }
}