package com.example.reservaplusapp.Body

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.reservaplusapp.Apis.RetrofitInstance
import com.example.reservaplusapp.Clases.ReservaInfo
import com.example.reservaplusapp.R
import kotlinx.coroutines.launch
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import com.example.reservaplusapp.Clases.Habitacion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


@Composable
fun MainBody(
    modifier: Modifier = Modifier,
    reservasViewModel: ReservasViewModel = viewModel()
) {
    val primaryColor = Color(0xFF57BDD3)

    LaunchedEffect(Unit) {
        reservasViewModel.fetchReservas()
    }

    val reservas = reservasViewModel.reservasList.value

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (reservas.isEmpty()) {
            item {
                Text(
                    "No se encontraron reservas.",
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
            }
        } else {
            items(reservas) { reservaInfo ->
                ReservaItem(reservaInfo, primaryColor)
            }
        }
    }
}

@Composable
fun ReservaItem(
    reservaInfo: ReservaInfo,
    primaryColor: Color,
    reservasViewModel: ReservasViewModel = viewModel()
) {
    val reserva = reservaInfo.reserva
    val habitaciones = reservaInfo.habitaciones
    val servicios = reservaInfo.servicios
    val isLoading by reservasViewModel.isLoading
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Fondo decorativo con Canvas
            Canvas(
                modifier = Modifier.matchParentSize()
            ) {
                val path = Path()
                path.moveTo(0f, 0f)
                path.lineTo(size.width, 0f)
                path.lineTo(size.width, size.height * 0.3f)
                path.quadraticBezierTo(
                    size.width * 0.5f,
                    size.height * 0.2f,
                    0f,
                    size.height * 0.4f
                )
                path.close()

                drawPath(
                    path = path,
                    color = primaryColor.copy(alpha = 0.2f)
                )

                // Círculos decorativos
                drawCircle(
                    color = primaryColor.copy(alpha = 0.1f),
                    radius = 60.dp.toPx(),
                    center = Offset(size.width * 0.9f, size.height * 0.1f)
                )
                drawCircle(
                    color = primaryColor.copy(alpha = 0.05f),
                    radius = 40.dp.toPx(),
                    center = Offset(size.width * 0.1f, size.height * 0.8f)
                )
            }

            // Contenido del Card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Reserva",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = primaryColor
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Fecha formateada
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                val fechaInicio =
                    ZonedDateTime.parse(reserva.fecha_inicio_reserva).format(formatter)
                val fechaFin = ZonedDateTime.parse(reserva.fecha_final_reserva).format(formatter)

                Text(
                    text = "$fechaInicio - $fechaFin",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                AsyncImage(
                    model = habitaciones.firstOrNull()?.habitacion?.imagen_slug,
                    contentDescription = "Imagen de Habitación",
                    modifier = Modifier
                        .size(250.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Capacidad",
                        tint = primaryColor
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${habitaciones.firstOrNull()?.habitacion?.personas ?: 0} personas",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Número de Habitación",
                        tint = primaryColor
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Habitación ${reserva.Numero_de_habitacion}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = when (reserva.estado) {
                            "pendiente" -> Icons.Default.Refresh
                            "en curso" -> Icons.Default.CheckCircle
                            else -> Icons.Default.Info
                        },
                        contentDescription = "Estado de la Reserva",
                        tint = when (reserva.estado) {
                            "pendiente" -> Color(0xFFFFA500) // Naranja
                            "en curso" -> Color(0xFF00FF00)  // Verde
                            else -> Color(0xFF808080)        // Gris
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = reserva.estado.capitalize(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = when (reserva.estado) {
                            "pendiente" -> Color(0xFFFFA500) // Naranja
                            "en curso" -> Color(0xFF00FF00)  // Verde
                            else -> Color(0xFF808080)        // Gris
                        }
                    )
                }

                if (servicios.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Servicios Incluidos",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = primaryColor
                    )
                    servicios.forEach { servicio ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Servicio",
                                tint = primaryColor
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = servicio.nombre_servicio,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }

                if (reserva.estado == "pendiente") {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            reservasViewModel.cancelarReserva(reserva.id) { isSuccess, message ->
                                dialogMessage = message
                                showDialog = true
                            }
                        }, enabled = !isLoading,
                        colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(ButtonDefaults.IconSize),
                                color = Color.White
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Cancelar Reserva",
                                modifier = Modifier.size(ButtonDefaults.IconSize)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Cancelar Reserva")
                        }
                    }
                }
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    Text(text = if (dialogMessage.contains("exitosamente")) "Éxito" else "Error")
                },
                text = {
                    Text(text = dialogMessage)
                },
                confirmButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Aceptar")
                    }
                }
            )
        }
    }

}

class ReservasViewModel : ViewModel() {
    var reservasList = mutableStateOf<List<ReservaInfo>>(emptyList())
        private set
    var isLoading = mutableStateOf(false)
        private set

    fun fetchReservas() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getReservas()
                if (response.isSuccessful) {
                    reservasList.value = response.body()?.reservas_info ?: emptyList()
                } else {
                    Log.e("ReservasViewModel", "Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("ReservasViewModel", "Exception: ${e.message}")
            }
        }
    }

    fun cancelarReserva(reservaId: Int, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val response = RetrofitInstance.api.cancelarReserva(reservaId)
                Log.d("id de reservas",reservaId.toString())
                Log.d("respuesta",response.body().toString())
                if (response.isSuccessful) {
                    onResult(true, "Reserva cancelada exitosamente.")
                    fetchReservas() // Actualiza la lista después de cancelar
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Error desconocido"
                    onResult(false, errorMessage)
                }
            } catch (e: Exception) {
                onResult(false, e.message ?: "Error desconocido")
            } finally {
                isLoading.value = false
            }
        }
    }
}








