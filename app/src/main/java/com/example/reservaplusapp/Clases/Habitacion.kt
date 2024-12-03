package com.example.reservaplusapp.Clases



data class HabitacionWrapper(
    val habitacion: Habitacion
)

data class Habitacion(
    val id: Int,
    val habitacion: Int,
    val personas: Int,
    val imagen_slug: String
)

