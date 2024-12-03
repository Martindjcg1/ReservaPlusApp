package com.example.reservaplusapp.Body

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.reservaplusapp.Apis.RetrofitInstance
import com.example.reservaplusapp.Clases.Servicio


import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ServiciosContent(modifier: Modifier = Modifier) {
    // Estado para almacenar la lista de servicios
    val serviciosList = remember { mutableStateOf<List<Servicio>>(emptyList()) }
    // Estado para manejar el estado de la carga
    val isLoading = remember { mutableStateOf(true) }

    // Obtener el contexto dentro de la función @Composable
    val context = LocalContext.current

    // Llamada asíncrona para obtener los servicios cuando el Composable se crea
    LaunchedEffect(Unit) { // Esto asegura que se ejecute solo una vez
        fetchServicios(context) { servicios ->
            serviciosList.value = servicios ?: emptyList()
            isLoading.value = false
        }
    }

    // Mostrar un loading si estamos esperando la respuesta de la API
    if (isLoading.value) {
        CircularProgressIndicator()
    } else {
        // Mostrar la lista de servicios cuando ya se haya cargado
        LazyColumn(modifier = modifier) {
            items(serviciosList.value) { servicio ->
                ServicioCard(servicio = servicio)
            }
        }
    }
}

@Composable
fun ServicioCard(servicio: Servicio, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),

        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen del servicio

            Image(
                painter = rememberAsyncImagePainter(servicio.slug),
                contentDescription = "Imagen de ${servicio.nombre}",
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Información del servicio
            Column {
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
                    fontWeight = FontWeight.Medium,
                    color = Color.Green
                )
            }
        }
    }
}


//

fun fetchServicios(context: Context, onResult: (List<Servicio>?) -> Unit) {
    RetrofitInstance.api.getServicios().enqueue(object : Callback<ResponseBody> {
        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            if (response.isSuccessful) {
                try {
                    // Obtener el cuerpo de la respuesta
                    val responseBody = response.body()

                    // Si la respuesta no es nula, convertirla a una lista de objetos 'Servicio'
                    responseBody?.let {
                        val json = it.string() // Convertir el ResponseBody en String
                        Log.d("fetchServicios", "Respuesta completa: $json")
                        val servicios = Gson().fromJson(json, Array<Servicio>::class.java).toList() // Convertir a lista de 'Servicio'
                        Log.d("fetchServicios", "Servicios procesados: $servicios")
                        // Pasar la lista de servicios a la función callback
                        onResult(servicios)
                    } ?: run {
                        onResult(null) // Si no hay respuesta, retornar null
                    }

                } catch (e: Exception) {
                    Log.e("fetchServicios", "Error al procesar los servicios: ${e.message}")
                    onResult(null)
                }
            } else {
                Log.e("fetchServicios", "Error: ${response.code()}")
                onResult(null)
            }
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            Log.e("fetchServicios", "Error: ${t.message}")
            onResult(null)
        }
    })
}


//