package com.example.reservaplusapp.Body

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import coil.compose.rememberImagePainter
import java.time.LocalDate



data class Servicio(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val imagen: String,
    val precio: Double
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiciosChooseScreen(
    habitacionId: Int,
    numeroHabitacion: Int,
    startDate: LocalDate,
    endDate: LocalDate,
    navController: NavController
) {
    val servicios = listOf(
        Servicio(1, "Spa", "Relájate con nuestros tratamientos de spa", "https://example.com/spa.jpg", 50.0),
        Servicio(2, "Gimnasio", "Mantén tu rutina de ejercicios", "https://example.com/gym.jpg", 20.0),
        Servicio(3, "Desayuno buffet", "Disfruta de un desayuno completo", "https://example.com/breakfast.jpg", 15.0),
        Servicio(4, "Tour guiado", "Conoce la ciudad con nuestros guías expertos", "https://example.com/tour.jpg", 40.0),
        Servicio(5, "Alquiler de bicicletas", "Explora los alrededores en bicicleta", "https://example.com/bike.jpg", 10.0)
    )

    var selectedServicios by remember { mutableStateOf(setOf<Int>()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Servicios Adicionales") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Regresar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF57BDD3),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(bottom = 64.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.TopCenter)
            ) {
                item {
                    Text(
                        text = "Selecciona servicios adicionales",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF57BDD3),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                items(servicios) { servicio ->
                    ServicioCard(
                        servicio = servicio,
                        isSelected = selectedServicios.contains(servicio.id),
                        onSelectChanged = { isSelected ->
                            selectedServicios = if (isSelected) {
                                selectedServicios + servicio.id
                            } else {
                                selectedServicios - servicio.id
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            Button(
                onClick = {
                    val serviciosIds = selectedServicios.joinToString(",")
                    navController.navigate(
                        "formulario/$habitacionId/$numeroHabitacion/${startDate.toString()}/${endDate.toString()}/$serviciosIds"
                    )
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF57BDD3))
            ) {
                Text("Continuar", fontSize = 18.sp)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicioCard(
    servicio: Servicio,
    isSelected: Boolean,
    onSelectChanged: (Boolean) -> Unit
) {
    val cardBackgroundColor = if (isSelected) Color(0xFFE3F2FD) else MaterialTheme.colorScheme.surface

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = cardBackgroundColor),
        onClick = { onSelectChanged(!isSelected) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberImagePainter(servicio.imagen),
                contentDescription = servicio.nombre,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 16.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = servicio.nombre,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = servicio.descripcion,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = "$${servicio.precio}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF57BDD3)
                )
            }
        }
    }
}