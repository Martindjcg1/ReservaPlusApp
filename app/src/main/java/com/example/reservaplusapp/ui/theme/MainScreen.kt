package com.example.reservaplusapp.ui.theme


import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavType
import coil.compose.rememberAsyncImagePainter
import com.example.reservaplusapp.Body.MainBody
import com.example.reservaplusapp.Body.ProfileContent
import com.example.reservaplusapp.Body.ReservasContent
import com.example.reservaplusapp.Body.ServiciosContent
import com.example.reservaplusapp.Footer.MainFooter
import com.example.reservaplusapp.Header.MainHeader
import com.example.reservaplusapp.R
import com.example.reservaplusapp.Apis.RetrofitInstance
import com.example.reservaplusapp.Body.DetallesHabitacionScreen
import com.example.reservaplusapp.Body.HabitacionesScreen
import com.example.reservaplusapp.Clases.Servicio
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.reservaplusapp.Body.FormularioHabitacionScreen


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    var selectedTab by remember { mutableStateOf(0) }
    var showNotifications by remember { mutableStateOf(false) }
    var notifications by remember { mutableStateOf(listOf("Nueva reserva disponible", "¡Oferta especial!")) }

    val navHostController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "ReservaPlus Logo",
                            modifier = Modifier.size(40.dp)
                        )
                        Text(
                            "ReservaPlus",
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                },
                actions = {
                    Box {
                        IconButton(onClick = { showNotifications = true }) {
                            BadgedBox(
                                badge = {
                                    if (notifications.isNotEmpty()) {
                                        Badge {
                                            Text(notifications.size.toString())
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    Icons.Default.Notifications,
                                    contentDescription = "Notificaciones",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF57BDD3),
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        },
        bottomBar = {
            MainFooter(
                selectedTab = selectedTab,
                onTabSelected = {
                    selectedTab = it
                    when (it) {
                        0 -> navHostController.navigate("home")
                        1 -> navHostController.navigate("reservas")
                        2 -> navHostController.navigate("servicios")
                        3 -> navHostController.navigate("profile")
                    }
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navHostController,
            startDestination = "home"
        ) {
            composable("home") {
                MainBody(modifier = Modifier.padding(paddingValues))
            }
            composable("reservas") {
                ReservasContent(modifier = Modifier.padding(paddingValues),
                    navController = navHostController)
            }
            composable("servicios") {
                ServiciosContent(modifier = Modifier.padding(paddingValues))
            }
            composable("profile") {
                ProfileContent(
                    modifier = Modifier.padding(paddingValues),
                    navController = navController,
                    onLogout = {
                        navController.navigate("login") {
                            popUpTo("main") { inclusive = true }
                        }
                    }
                )
            }
            composable(
                "habitaciones/{startDate}/{endDate}",
                arguments = listOf(
                    navArgument("startDate") { type = NavType.StringType },
                    navArgument("endDate") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val startDateString = backStackEntry.arguments?.getString("startDate")
                val endDateString = backStackEntry.arguments?.getString("endDate")
                val startDate = LocalDate.parse(startDateString)
                val endDate = LocalDate.parse(endDateString)
                HabitacionesScreen(
                    startDate = startDate,
                    endDate = endDate,
                    navController = navHostController
                )
            }
            composable(
                route = "detalle/{id}/{startDate}/{endDate}",
                arguments = listOf(
                    navArgument("id") { type = NavType.IntType },
                    navArgument("startDate") { type = NavType.StringType },
                    navArgument("endDate") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: 0
                val startDate = LocalDate.parse(backStackEntry.arguments?.getString("startDate"))
                val endDate = LocalDate.parse(backStackEntry.arguments?.getString("endDate"))
                DetallesHabitacionScreen(
                    habitacionId = id,
                    startDate = startDate,
                    endDate = endDate,
                    navController = navHostController
                )
            }

            composable(
                route = "formulario/{id}/{numeroHabitacion}/{startDate}/{endDate}",
                arguments = listOf(
                    navArgument("id") { type = NavType.IntType },
                    navArgument("numeroHabitacion") { type = NavType.IntType },
                    navArgument("startDate") { type = NavType.StringType },
                    navArgument("endDate") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: 0
                val numeroHabitacion = backStackEntry.arguments?.getInt("numeroHabitacion") ?: 0
                val startDate = LocalDate.parse(backStackEntry.arguments?.getString("startDate"))
                val endDate = LocalDate.parse(backStackEntry.arguments?.getString("endDate"))
                FormularioHabitacionScreen(
                    habitacionId = id,
                    numeroHabitacion = numeroHabitacion,
                    startDate = startDate,
                    endDate = endDate,
                    navController = navHostController
                )
            }
        }
    }
    if (showNotifications) {
        NotificationsDialog(
            notifications = notifications,
            onDismiss = { showNotifications = false },
            onDeleteNotification = { notification ->
                notifications = notifications.filter { it != notification }
            }
        )
    }
}

@Composable
fun NotificationsDialog(
    notifications: List<String>,
    onDismiss: () -> Unit,
    onDeleteNotification: (String) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            ) {
                // Custom background with Canvas
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val path = Path().apply {
                        moveTo(0f, 0f)
                        lineTo(size.width, 0f)
                        quadraticBezierTo(
                            size.width - 100f,
                            size.height / 2,
                            size.width,
                            size.height
                        )
                        lineTo(0f, size.height)
                        close()
                    }
                    drawPath(
                        path = path,
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF57BDD3),
                                Color(0xFF3CA3B9)
                            )
                        )
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(
                        "Notificaciones",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    if (notifications.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "No hay notificaciones pendientes",
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(notifications) { notification ->
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.White.copy(alpha = 0.9f)
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            notification,
                                            modifier = Modifier.weight(1f),
                                            color = Color.Black
                                        )
                                        IconButton(
                                            onClick = { onDeleteNotification(notification) }
                                        ) {
                                            Icon(
                                                Icons.Default.Close,
                                                contentDescription = "Eliminar",
                                                tint = Color.Red
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    TextButton(
                        onClick = onDismiss,
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Cerrar", color = Color.White)
                    }
                }
            }
        }
    }
}