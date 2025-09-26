package org.example.model

sealed class EstadoReserva {
    object  Pendiente : EstadoReserva()
    object  Confirmada : EstadoReserva()
    object Cancelada : EstadoReserva()
    data class Error(val mensaje: String) : EstadoReserva()
}