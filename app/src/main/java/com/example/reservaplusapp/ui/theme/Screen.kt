package com.example.reservaplusapp.ui.theme

import com.example.reservaplusapp.Body.Habitacion

sealed class Screen {
    object Home : Screen()
    object Search : Screen()
    object TusReservas : Screen()
    object Servicios : Screen()
    object Profile : Screen()
    data class Detail(val habitacion: Habitacion) : Screen()
}