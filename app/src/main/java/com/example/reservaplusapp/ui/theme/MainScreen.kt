package com.example.reservaplusapp.ui.theme



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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.reservaplusapp.Background.Background
import com.example.reservaplusapp.Body.MainBody
import com.example.reservaplusapp.Footer.MainFooter
import com.example.reservaplusapp.Header.MainHead
import com.example.reservaplusapp.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    var isDrawerOpen by remember { mutableStateOf(false) }

    Background {
        Scaffold(
            topBar = { MainHead(onMenuClick = { isDrawerOpen = true }) },
            bottomBar = { MainFooter(onNavigate = { /* TODO: Implement navigation */ }) }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                MainBody()
            }
        }

        if (isDrawerOpen) {
            ModalDrawerSheet {
                Text("Menu", modifier = Modifier.padding(16.dp))
                Divider()
                NavigationDrawerItem(
                    label = { Text("Reservas") },
                    selected = false,
                    onClick = { isDrawerOpen = false }
                )
                NavigationDrawerItem(
                    label = { Text("Servicios") },
                    selected = false,
                    onClick = { isDrawerOpen = false }
                )
                NavigationDrawerItem(
                    label = { Text("Perfil") },
                    selected = false,
                    onClick = { isDrawerOpen = false }
                )
                NavigationDrawerItem(
                    label = { Text("Logout") },
                    selected = false,
                    onClick = {
                        isDrawerOpen = false
                        navController.navigate("login") {
                            popUpTo("main") { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}