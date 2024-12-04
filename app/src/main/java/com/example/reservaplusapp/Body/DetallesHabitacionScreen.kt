package com.example.reservaplusapp.Body

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.time.LocalDate

@Composable
fun DetallesHabitacionScreen(
    id: Int,
    startDate: LocalDate,
    endDate: LocalDate,
    navController: NavController
) {
    // Usamos un Box para centrar el contenido
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center // Centra el contenido dentro del Box
    ) {
        // Usamos una columna dentro del Box para organizar los elementos verticalmente
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, // Centra horizontalmente los elementos dentro de la columna
            verticalArrangement = Arrangement.Center // Centra verticalmente los elementos dentro de la columna
        ) {
            Text("Detalles de la habitación $id")
            Text("Fecha de inicio: $startDate")
            Text("Fecha de fin: $endDate")
            Spacer(modifier = Modifier.height(16.dp)) // Añade un espacio entre los textos y el botón
            Button(onClick = { navController.popBackStack() }) {
                Text("Volver")
            }
        }
    }
}
