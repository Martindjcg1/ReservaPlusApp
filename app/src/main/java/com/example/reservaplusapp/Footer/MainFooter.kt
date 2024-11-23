package com.example.reservaplusapp.Footer



import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.reservaplusapp.R

@Composable
fun MainFooter(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    NavigationBar(
        containerColor = Color.White,
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Inicio") },
            selected = selectedTab == 0,
            onClick = { onTabSelected(0) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            label = { Text("Buscar") },
            selected = selectedTab == 1,
            onClick = { onTabSelected(1) }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.hotelogo),
                    contentDescription = "Reservas",
                    modifier = Modifier
                        .size(30.dp)
                        .padding(4.dp) // Aumenta o reduce el espacio alrededor si es necesario
                )
            },
            label = { Text("Reservas") },
            selected = selectedTab == 2,
            onClick = { onTabSelected(2) }
        )


        NavigationBarItem(
            icon = { Icon(Icons.Default.ThumbUp, contentDescription = "Servicios") },
            label = { Text("Servicios") },
            selected = selectedTab == 3,
            onClick = { onTabSelected(3) }
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Perfil") },
            selected = selectedTab == 4,
            onClick = { onTabSelected(4) }
        )
    }
}