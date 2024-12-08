package com.example.reservaplusapp.Clases

import com.google.gson.annotations.SerializedName


data class HabitacionWrapper(
    val habitacion: Habitacion
)

data class Habitacion(
    val id: Int,
    val habitacion: Int,
    val personas: Int,
    val imagen_slug: String
)

data class Habitaciones(
    val id: Int,
    val nombre: String,
    val precio: Double,
    val cupo: Int,
    val ubicacion: String,
    val ventanas: Int,
    val camas: String,
    val numero_de_camas: Int,
    val aire_acondicionado: Boolean,
    val jacuzzi: Boolean,
    val Numero_de_habitacion: Int,
    val disponibilidad: String,
    val slug: String?
)

data class HabitacionesResponse(
    @SerializedName("habitaciones_disponibles")
    val habitacionesDisponibles: List<Habitaciones>
)


data class HabitacionDetalleResponse(
    val habitacion: Habitacion1,
    val detalle: DetalleHabitacion?,
    val fecha_inicio: String,
    val fecha_final: String
)

data class Habitacion1(
    val id: Int,
    val nombre: String,
    val precio: Double,
    val cupo: Int,
    val slug: String
)

data class DetalleHabitacion(
    val Numero_de_habitacion: Int,
    val ubicacion: String,
    val ventanas: Int,
    val camas: String,
    val numero_de_camas: Int,
    val aire_acondicionado: Boolean,
    val jacuzzi: Boolean,
    val disponibilidad: String
)
