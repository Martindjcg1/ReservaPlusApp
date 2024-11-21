package com.example.reservaplusapp.Footer



import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun MainFooter(onNavigate: (String) -> Unit) {
    NavigationBar {
        NavItem("Reservas", Icons.Default.DateRange, onNavigate)
        NavItem("Servicios", Icons.Default.List, onNavigate)
        NavItem("Perfil", Icons.Default.Person, onNavigate)
    }
}

@Composable
fun RowScope.NavItem(title: String, icon: ImageVector, onNavigate: (String) -> Unit) {
    NavigationBarItem(
        icon = { Icon(icon, contentDescription = title) },
        label = { Text(title) },
        selected = false, // TODO: Implement actual navigation state
        onClick = { onNavigate(title) }
    )
}