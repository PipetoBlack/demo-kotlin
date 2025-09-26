package service

import kotlinx.coroutines.delay
import org.example.model.*

// -----------------------------
// Tipos de cliente
// -----------------------------
enum class TipoCliente { REGULAR, VIP, PREMIUM }

// -----------------------------
// Función de descuento por cliente
// -----------------------------
fun descuentoPorCliente(tipo: TipoCliente): Double = when (tipo) {
    TipoCliente.REGULAR -> 0.05
    TipoCliente.VIP     -> 0.10
    TipoCliente.PREMIUM -> 0.15
}

// -----------------------------
// Entidades de reserva y resultado
// -----------------------------
data class Reserva(
    val viajes: List<Viaje>,
    val tipoCliente: TipoCliente
)

data class ResultadoReserva(
    val subtotal: Double,
    val descuento: Double,
    val impuestos: Double,
    val total: Double,
    val estado: EstadoReserva
)

// -----------------------------
// Catálogo inicial de viajes
// -----------------------------
fun inicializarCatalogo(): List<Viaje> = listOf(
    ViajeNacional("San Pedro de Atacama", 249_990.0, 4, temporadaBaja = true),
    ViajeNacional("Chiloé", 189_990.0, 3, temporadaBaja = false),
    ViajeInternacional("Buenos Aires", 329_990.0, 4, tasaInternacional = 0.08),
    ViajeInternacional("Madrid", 899_990.0, 7, tasaInternacional = 0.12)
)

// -----------------------------
// Cálculo de resumen de reserva
// -----------------------------
fun calcularResumen(reserva: Reserva): ResultadoReserva {
    val subtotal = reserva.viajes.sumOf { it.precioFinal() }
    val descuento = subtotal * descuentoPorCliente(reserva.tipoCliente)
    val impuestos = subtotal * 0.19
    val total = subtotal - descuento + impuestos

    return ResultadoReserva(
        subtotal = subtotal,
        descuento = descuento,
        impuestos = impuestos,
        total = total,
        estado = EstadoReserva.Pendiente
    )
}

// -----------------------------
// Procesamiento asíncrono de reserva
// -----------------------------
suspend fun procesarReserva(reserva: Reserva): EstadoReserva {
    delay(3000L) // Simula tiempo de procesamiento
    return try {
        val resumen = calcularResumen(reserva)
        if (resumen.total <= 0) EstadoReserva.Error("Total inválido")
        else EstadoReserva.Confirmada
    } catch (e: IllegalArgumentException) {
        EstadoReserva.Error("Datos inválidos: ${e.message}")
    } catch (e: Exception) {
        EstadoReserva.Error("Error inesperado: ${e.message}")
    }
}

// -----------------------------
// Impresión de catálogo
// -----------------------------
fun imprimirCatalogo(catalogo: List<Viaje>) {
    println("Catálogo de viajes:")
    catalogo.forEachIndexed { i, viaje ->
        println("[$i] ${viaje.destino} - ${viaje.categoria} - ${viaje.duracionDias} días - $${viaje.precioBase}")
    }
}

// -----------------------------
// Reporte de ventas
// -----------------------------
data class ReporteVentas(
    val totalIngresos: Double,
    val ingresosNacionales: Double,
    val ingresosInternacionales: Double,
    val viajesCortos: List<String>
)

fun generarReporteIngresos(viajesVendidos: List<Viaje>): ReporteVentas {
    val total = viajesVendidos.sumOf { it.precioFinal() }
    val ingresosNac = viajesVendidos
        .filterIsInstance<ViajeNacional>()
        .sumOf { it.precioFinal() }
    val ingresosInt = viajesVendidos
        .filterIsInstance<ViajeInternacional>()
        .sumOf { it.precioFinal() }
    val cortos = viajesVendidos
        .filter { it.duracionDias <= 3 }
        .map { it.destino }

    return ReporteVentas(total, ingresosNac, ingresosInt, cortos)
}