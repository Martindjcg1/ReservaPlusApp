package com.example.reservaplusapp.Body

import android.util.Log
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
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.example.reservaplusapp.Clases.Servicio
import com.example.reservaplusapp.Models.ServiciosViewModel2
import java.time.LocalDate





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiciosChooseScreen(
    habitacionId: Int,
    numeroHabitacion: Int,
    startDate: LocalDate,
    endDate: LocalDate,
    navController: NavController,
    viewModel: ServiciosViewModel2
) {
    val servicios = viewModel.servicios
    val selectedServicios = viewModel.selectedServicios

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
            if (servicios.isEmpty()) {
                Text(
                    text = "Cargando servicios...",
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 16.sp
                )
            } else {
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
                                viewModel.toggleServicioSelection(servicio.id)
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }

            Button(
                onClick = {
                    val serviciosIds = selectedServicios.joinToString(",")
                    Log.d("los servicios:", serviciosIds)
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
            AsyncImage(
                model = servicio.slug,
                contentDescription = "Imagen del servicio",
                modifier = Modifier
                    .size(80.dp) // Ajustar el tamaño de la imagen
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