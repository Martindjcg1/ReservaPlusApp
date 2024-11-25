package com.example.reservaplusapp.Body

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reservaplusapp.R
import com.example.reservaplusapp.Clases.Servicio


@Composable
fun MainBody(
    modifier: Modifier = Modifier,
    onHabitacionClick: (Habitacion) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                "Habitaciones Disponibles",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        items(getHabitaciones()) { habitacion ->
            HabitacionCard(
                habitacion = habitacion,
                onClick = { onHabitacionClick(habitacion) }
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Servicios Destacados",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        items(getServicios()) { servicio ->
            ServicioCard(servicio)
        }
    }
}

data class Habitacion(
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val rating: Float
)



// Actualizar HabitacionCard para manejar clicks
@Composable
fun HabitacionCard(
    habitacion: Habitacion,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.banner),
                contentDescription = habitacion.nombre,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp)
            ) {
                Text(
                    text = habitacion.nombre,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = habitacion.descripcion,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(5) { index ->
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = if (index < habitacion.rating) Color(0xFFFFC107) else Color.Gray,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Text(
                        text = "${habitacion.rating}",
                        color = Color.Gray,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "$${habitacion.precio}/noche",
                    color = Color(0xFF57BDD3),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun ServicioCard(servicio: Servicio) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.banner),
                contentDescription = servicio.nombre,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp)
            ) {
                Text(
                    text = servicio.nombre,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = servicio.descripcion,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(5) { index ->
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            //tint = if (index < servicio.rating) Color(0xFFFFC107) else Color.Gray,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Text(
                        text = "",
                        color = Color.Gray,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "$${servicio.precio}",
                    color = Color(0xFF57BDD3),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

// Funciones auxiliares para datos de ejemplo
private fun getHabitaciones(): List<Habitacion> {
    return listOf(
        Habitacion("Suite Presidencial", "Vista al mar, 2 habitaciones", 299.99, 4.8f),
        Habitacion("Habitación Deluxe", "Vista a la ciudad", 199.99, 4.5f),
        Habitacion("Suite Junior", "Balcón privado", 249.99, 4.7f)
    )
}

private fun getServicios(): List<Servicio> {
    return listOf(
        //Servicio("Spa Premium", "Masaje relajante de 60 min", 89.99, 4.9f),
        //Servicio("Restaurante Gourmet", "Cena para dos personas", 129.99, 4.6f),
        //Servicio("Gimnasio", "Acceso ilimitado", 19.99, 4.4f)
    )
}