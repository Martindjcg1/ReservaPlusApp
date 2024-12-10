package com.example.reservaplusapp.Clases


data class ReservasResponse(
    val reservas_info: List<ReservaInfo>
)

data class ReservaInfo(
    val reserva: Reserva,
    val habitaciones: List<HabitacionWrapper>,
    val servicios: List<ServicioInfo>,
    val usuario_id: Int
)

data class Reserva(
    val id: Int,
    val fecha_inicio_reserva: String,
    val fecha_final_reserva: String,
    val estado: String,
    val Numero_de_habitacion: Int,
    val costo: Double
)


data class FechasReserva(
    val fecha_inicio: String,
    val fecha_final: String
)

data class FechasResponse(
    val mensaje: String,
    val fecha_inicio: String,
    val fecha_final: String
)


data class CheckoutRequest(
    val fecha_inicio: String,
    val fecha_final: String,
    val numero_personas: Int,
    val servicios: List<Int>
)