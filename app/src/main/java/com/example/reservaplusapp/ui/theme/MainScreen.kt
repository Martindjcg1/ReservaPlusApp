package com.example.reservaplusapp.ui.theme



import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.reservaplusapp.Body.Habitacion
import com.example.reservaplusapp.Body.MainBody
import com.example.reservaplusapp.Footer.MainFooter

import com.example.reservaplusapp.Header.MainHeader
import com.example.reservaplusapp.R


sealed class Screen {
    object Home : Screen()
    object Search : Screen()
    object TusReservas : Screen()
    object Servicios : Screen()
    object Profile : Screen()
    data class Detail(val habitacion: Habitacion) : Screen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = { MainHeader() },
        bottomBar = {
            MainFooter(
                selectedTab = selectedTab,
                onTabSelected = {
                    selectedTab = it
                    currentScreen = when(it) {
                        0 -> Screen.Home
                        1 -> Screen.Search
                        2 -> Screen.TusReservas
                        3 -> Screen.Servicios
                        4 -> Screen.Profile
                        else -> Screen.Home
                    }
                }
            )
        }
    ) { paddingValues ->
        when (currentScreen) {
            is Screen.Home -> MainBody(
                modifier = Modifier.padding(paddingValues),
                onHabitacionClick = { habitacion ->
                    currentScreen = Screen.Detail(habitacion)
                }
            )
            is Screen.Search -> SearchContent(Modifier.padding(paddingValues))
            is Screen.TusReservas -> ReservasContent(Modifier.padding(paddingValues))
            is Screen.Servicios -> ServiciosContent(Modifier.padding(paddingValues))
            is Screen.Profile -> ProfileContent(Modifier.padding(paddingValues))
            is Screen.Detail -> DetailScreen(
                habitacion = (currentScreen as Screen.Detail).habitacion,
                onBackClick = { currentScreen = Screen.Home },
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}

@Composable
fun DetailScreen(
    habitacion: Habitacion,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF57BDD3), // Azul claro
                        Color(0xFF0A3D62)  // Azul oscuro
                    )
                )
            )
    )
    {
        LazyColumn {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.banner),
                        contentDescription = habitacion.nombre,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.TopStart)
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(5) { index ->
                            Icon(
                                Icons.Default.Star,
                                contentDescription = null,
                                tint = if (index < habitacion.rating) Color(0xFFFFC107) else Color.Gray,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = habitacion.nombre,
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = habitacion.descripcion,
                        color = Color.Gray,
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Our Package",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(listOf(150, 220, 300)) { precio ->
                            Card(
                                modifier = Modifier
                                    .width(120.dp)
                                    .height(80.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFF3D5AFE)
                                )
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(16.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "$$precio",
                                        color = Color.White,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "per night",
                                        color = Color.White.copy(alpha = 0.7f),
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "More Photos",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(3) {
                            Image(
                                painter = painterResource(id = R.drawable.banner),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(16.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                        item {
                            Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color.DarkGray),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Add,
                                    contentDescription = "Ver más fotos",
                                    tint = Color.White,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { /* Implementar reserva */ },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF3D5AFE)
                        )
                    ) {
                        Text(
                            "Reservar Ahora",
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }
}







@Composable
fun SearchContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            onSearch = { /* Implementar búsqueda */ }
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(listOf("Lugar 1", "Lugar 2", "Lugar 3")) { place ->
                SearchResultCard(place)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            onSearch(it)
        },
        modifier = modifier,
        placeholder = { Text("Buscar lugares...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF57BDD3),
            unfocusedBorderColor = Color.Gray
        )
    )
}

@Composable
fun SearchResultCard(place: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.banner),
                contentDescription = place,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = place,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Descripción breve del lugar",
                    color = Color.Gray
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFF57BDD3),
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "4.5",
                        color = Color(0xFF57BDD3),
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
            Button(
                onClick = { /* Implementar reserva */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF57BDD3)
                ),
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Reservar")
            }
        }
    }
}

@Composable
fun ReservasContent(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        Text(
            "Reservas",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun ServiciosContent(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        Text(
            "Servicios",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun ProfileContent(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        Text(
            "Perfil",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}