package com.example.reservaplusapp.ui.theme

import com.example.reservaplusapp.Body.HabitacionesDisponibles
import java.time.LocalDate


sealed class Screen {
    object Home : Screen()
    object TusReservas : Screen()
    object Servicios : Screen()
    object Profile : Screen()
    data class Detail(val habitacion: HabitacionesDisponibles) : Screen()
    data class Habitaciones(val startDate: LocalDate, val endDate: LocalDate) : Screen()
}