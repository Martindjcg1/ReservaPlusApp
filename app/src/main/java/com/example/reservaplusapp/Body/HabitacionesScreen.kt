package com.example.reservaplusapp.Body

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import java.time.LocalDate

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
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val habitaciones = remember {
        listOf(
            HabitacionesDisponibles(
                id = 1,
                nombre = "Habitación lujosa",
                ubicacion = "San Pedro Cahro Michoacán",
                precio = 1500.00,
                imagen = "https://example.com/imagen_habitacion_lujosa.jpg",
                descripcion = "Habitación de lujo con todas las comodidades",
                cupo = 4,
                caracteristicas = mapOf(
                    "Jacuzzi" to "Sí",
                    "Ventanas" to "2",
                    "Camas" to "Camas cómodas",
                    "Aire Acondicionado" to "Sí"
                )
            ),
            HabitacionesDisponibles(
                id = 2,
                nombre = "Habitación económica",
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
                nombre = "Habitación 5 estrellas",
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

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
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

        items(habitaciones) { habitacion ->
            HabitacionCard(
                habitacion = habitacion,
                startDate = startDate,
                endDate = endDate,
                onVerDetalles = {
                    navController.navigate(
                        "detalles/${habitacion.id}/${startDate}/${endDate}"
                    )
                }
            )
        }
    }
}


@Composable
fun HabitacionCard(
    habitacion: HabitacionesDisponibles,
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
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // Corregido aquí
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Imagen de la habitación
            AsyncImage(
                model = habitacion.imagen,
                contentDescription = "Imagen de la ${habitacion.nombre}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Información principal de la habitación
            Text(
                text = habitacion.nombre,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = habitacion.ubicacion,
                fontSize = 16.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Precio por noche: \$${habitacion.precio}",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF57BDD3)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Botón para ver más detalles
            Button(
                onClick = onVerDetalles,
                modifier = Modifier.align(Alignment.End),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF57BDD3) // Actualizado
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
