package com.example.reservaplusapp.Body

import android.util.Log
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
import coil.compose.AsyncImage
import com.example.reservaplusapp.Clases.Habitacion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


@Composable
fun MainBody(
    modifier: Modifier = Modifier,
    reservasViewModel: ReservasViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        reservasViewModel.fetchReservas()
    }

    // Obtén las reservas del ViewModel
    val reservas = reservasViewModel.reservasList.value
    Log.e("resulatdo",reservas.toString())

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp)
    ) {
        if (reservas.isEmpty()) {
            item {
                Text("No se encontraron reservas.", modifier = Modifier.fillMaxWidth())
            }
        } else {
            items(reservas) { reservaInfo ->
                ReservaItem(reservaInfo)
            }
        }
    }
}

@Composable
fun ReservaItem(reservaInfo: ReservaInfo) {
    val reserva = reservaInfo.reserva
    val habitaciones = reservaInfo.habitaciones
    val servicios = reservaInfo.servicios

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Información de la reserva
            Text(
                text = "Reserva del ${reserva.fecha_inicio_reserva} al ${reserva.fecha_final_reserva}",

                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Número de Habitación: ${reserva.Numero_de_habitacion}"

            )

            Spacer(modifier = Modifier.height(8.dp))

            // Habitaciones asociadas
            habitaciones.forEach { habitacionInfo ->
                val habitacion = habitacionInfo.habitacion
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    // Imagen de la habitación
                    AsyncImage(
                        model = habitacion.imagen_slug,
                        contentDescription = "Imagen de Habitación",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Personas: ${habitacion.personas}",

                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Servicios asociados
            if (servicios.isNotEmpty()) {
                Text(
                    text = "Servicios:",

                    fontWeight = FontWeight.Bold
                )
                servicios.forEach { servicio ->
                    Text(
                        text = "Servicio: ${servicio.nombre_servicio}",

                    )
                }
            }
        }
    }
}


class ReservasViewModel : ViewModel() {
    var reservasList = mutableStateOf<List<ReservaInfo>>(emptyList())
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
}





