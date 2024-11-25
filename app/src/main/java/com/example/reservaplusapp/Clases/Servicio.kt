package com.example.reservaplusapp.Clases

data class Servicio(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val imagen: String,
    val disponibilidad: Boolean,
    val precio: Double,
    val slug: String
)