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

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
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
                        // Simular navegación a la pantalla de habitaciones
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
