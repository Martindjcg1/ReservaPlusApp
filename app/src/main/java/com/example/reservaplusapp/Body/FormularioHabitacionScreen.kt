package com.example.reservaplusapp.Body

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioHabitacionScreen(
    habitacionId: Int,
    numeroHabitacion: Int,
    startDate: LocalDate,
    endDate: LocalDate,
    navController: NavController,
    viewModel: HabitacionesViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var numeroPersonas by remember { mutableStateOf("") }
    var selectedServices by remember { mutableStateOf(setOf<String>()) }

    val servicios = listOf(
        "Spa" to 25.00,
        "Gym" to 25.00,
        "Cancha de tenis" to 30.00
    )

    val habitacion by viewModel.selectedHabitacion

    LaunchedEffect(habitacionId) {
        viewModel.setSelectedHabitacion(habitacionId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Título
        Text(
            text = "Reservar ${habitacion?.nombre ?: "Habitación"}",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF57BDD3),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Fechas
        Text(
            text = "Fecha de Inicio",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )
        OutlinedTextField(
            value = startDate.format(DateTimeFormatter.ISO_LOCAL_DATE),
            onValueChange = { },
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = Color.Black,
                disabledBorderColor = Color(0xFF57BDD3)
            )
        )

        Text(
            text = "Fecha Final",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )
        OutlinedTextField(
            value = endDate.format(DateTimeFormatter.ISO_LOCAL_DATE),
            onValueChange = { },
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = Color.Black,
                disabledBorderColor = Color(0xFF57BDD3)
            )
        )

        // Número de Habitación
        Text(
            text = "Número de Habitación",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )
        OutlinedTextField(
            value = numeroHabitacion.toString(),
            onValueChange = { },
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = Color.Black,
                disabledBorderColor = Color(0xFF57BDD3)
            )
        )

        // Número de Personas
        Text(
            text = "Número de Personas",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )
        OutlinedTextField(
            value = numeroPersonas,
            onValueChange = {
                if (it.all { char -> char.isDigit() }) {
                    numeroPersonas = it
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF57BDD3),
                unfocusedBorderColor = Color(0xFF57BDD3)
            )
        )

        // Servicios Adicionales
        Text(
            text = "Servicios Adicionales",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 8.dp)
        )
//Aqui agregar los servicios pero de la API no de la lista
        servicios.forEach { (servicio, precio) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = selectedServices.contains(servicio),
                    onCheckedChange = { checked ->
                        selectedServices = if (checked) {
                            selectedServices + servicio
                        } else {
                            selectedServices - servicio
                        }
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF57BDD3)
                    )
                )
                Text(
                    text = "$servicio - $${String.format("%.2f", precio)}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botones
        Button(
            onClick = {
                // Implementar lógica de pago
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF57BDD3)
            )
        ) {
            Text("Pagar con Stripe")
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = {
                navController.navigate("habitaciones/$startDate/$endDate") {
                    popUpTo("habitaciones/$startDate/$endDate") { inclusive = true }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color(0xFF57BDD3)
            )
        ) {
            Text("Volver a Habitaciones")
        }
    }
}