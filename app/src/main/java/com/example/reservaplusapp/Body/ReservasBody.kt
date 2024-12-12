package com.example.reservaplusapp.Body

import android.os.Build
import android.util.Log
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.reservaplusapp.Apis.RetrofitInstance
import com.example.reservaplusapp.Clases.FechasReserva
import com.example.reservaplusapp.Clases.FechasResponse
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState


import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservasContent(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    var startDate by remember { mutableStateOf<LocalDate?>(null) }
    var endDate by remember { mutableStateOf<LocalDate?>(null) }
    val calendarState = rememberUseCaseState()
    val fechasViewModel: FechasViewModel = viewModel()
    val fechasResponse = fechasViewModel.fechasResponse
    val isLoading = fechasViewModel.isLoading
    var fechasReserva by remember { mutableStateOf(FechasReserva(fecha_inicio = "", fecha_final = "")) }

    val today = remember { LocalDate.now() }
    val oneYearFromNow = remember { today.plusYears(1) }

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

                val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
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
                        yearSelection = true,
                        style = CalendarStyle.MONTH,
                        boundary = today..oneYearFromNow
                    ),
                    selection = CalendarSelection.Period { start, end ->
                        if (start >= today && end <= oneYearFromNow) {
                            startDate = start
                            endDate = end
                            fechasReserva = FechasReserva(fecha_inicio = startDate.toString(), fecha_final = endDate.toString())
                            fechasViewModel.validarFechas(fechasReserva)
                        }
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
                    if (startDate != null && endDate != null && !isLoading) {
                        if (fechasResponse != null) {
                            //Log.d("Fechas", fechasReserva.toString())
                            navController.navigate("habitaciones/${startDate}/${endDate}")
                        } else {
                            //Log.e("Error en las Fechas", "")
                        }
                    }
                },
                enabled = startDate != null && endDate != null && !isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF57BDD3)
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White
                    )
                } else {
                    Text("Confirmar")
                }
            }
        }
    }
}





class FechasViewModel : ViewModel() {
    var fechasResponse by mutableStateOf<FechasResponse?>(null)
        private set
    var isLoading by mutableStateOf(false)
        private set

    fun validarFechas(fechasReserva: FechasReserva) {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = RetrofitInstance.api.validarFechas(fechasReserva)
                if (response.isSuccessful) {
                    Log.d("Respuesta",response.body().toString())
                    fechasResponse = response.body()
                } else {
                    fechasResponse = null
                }
            } catch (e: Exception) {
                fechasResponse = null
            } finally {
                isLoading = false
            }
        }
    }
}
