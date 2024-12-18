package com.example.reservaplusapp.Body

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.reservaplusapp.Models.HabitacionesReservasViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
    serviciosIds: String,
    navController: NavController,
    viewModel: HabitacionesReservasViewModel = viewModel()
) {
    var numeroPersonas by remember { mutableStateOf("") }
    val context = LocalContext.current //

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 64.dp, start = 16.dp, end = 16.dp, bottom = 16.dp) // Añadido padding superior
            .verticalScroll(rememberScrollState())
    ) {


        OutlinedTextField(
            value = startDate.format(DateTimeFormatter.ISO_LOCAL_DATE),
            onValueChange = { },
            label = { Text("Fecha de Inicio") },
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = Color.Black,
                disabledBorderColor = Color(0xFF57BDD3)
            )
        )

        OutlinedTextField(
            value = endDate.format(DateTimeFormatter.ISO_LOCAL_DATE),
            onValueChange = { },
            label = { Text("Fecha Final") },
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = Color.Black,
                disabledBorderColor = Color(0xFF57BDD3)
            )
        )

        OutlinedTextField(
            value = numeroHabitacion.toString(),
            onValueChange = { },
            label = { Text("Número de Habitación") },
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = Color.Black,
                disabledBorderColor = Color(0xFF57BDD3)
            )
        )

        OutlinedTextField(
            value = numeroPersonas,
            onValueChange = {
                if (it.all { char -> char.isDigit() }) {
                    numeroPersonas = it
                }
            },
            label = { Text("Número de Personas") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF57BDD3),
                unfocusedBorderColor = Color(0xFF57BDD3)
            )
        )





        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val serviciosList = serviciosIds.split(",").mapNotNull { it.toIntOrNull() }
                CoroutineScope(Dispatchers.IO).launch {
                    val url = viewModel.crearCheckout(
                        habitacionId,
                        numeroHabitacion,
                        startDate.atTime(14, 0).toString(), // Hora fija: 14:00
                        endDate.atTime(11, 0).toString(),  // Hora fija: 11:00
                        numeroPersonas.toIntOrNull() ?: 1,
                        serviciosList
                    )
                    url?.let {
                        withContext(context = Dispatchers.Main) {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                           context.startActivity(intent)
                        }
                    }
                }
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