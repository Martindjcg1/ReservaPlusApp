package com.example.reservaplusapp.Body

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
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
import coil.compose.AsyncImage
import com.example.reservaplusapp.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun MainBody() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Banner()
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            Text("Tus Próximas Reservas", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
        }
        items(listOf("Reserva 1", "Reserva 2", "Reserva 3")) { reservation ->
            ReservationCard(reservation)
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Servicios Destacados", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
        }
        items(listOf("Servicio 1", "Servicio 2", "Servicio 3", "Servicio 4")) { service ->
            ServiceCard(service)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun Banner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.banner),
            contentDescription = "Banner",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
        )
        Text(
            text = "Bienvenido a ReservaPlus",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
        )
    }
}

@Composable
fun ReservationCard(reservation: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = reservation, style = MaterialTheme.typography.titleMedium)
            Text(text = "Fecha: ${SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Servicio: Ejemplo de Servicio", style = MaterialTheme.typography.bodyMedium)
            Button(
                onClick = { /* TODO: Implement reservation details */ },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Ver Detalles")
            }
        }
    }
}

@Composable
fun ServiceCard(service: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = R.drawable.service_placeholder),
                contentDescription = service,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = service, style = MaterialTheme.typography.titleMedium)
            Text(text = "Descripción breve del servicio.", style = MaterialTheme.typography.bodyMedium)
            Button(
                onClick = { /* TODO: Implement service booking */ },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Reservar")
            }
        }
    }
}

