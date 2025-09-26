import kotlinx.coroutines.runBlocking
import service.*
import org.example.model.EstadoReserva

fun main() = runBlocking {
    println("=== Bienvenido a TravelGo ===")

    // 1) Inicializar catálogo
    val catalogo = inicializarCatalogo()
    imprimirCatalogo(catalogo)

    // 2) Simular selección de viajes (ejemplo: Atacama + Buenos Aires)
    val seleccion = listOf(catalogo[0], catalogo[2])

    // 3) Definir tipo de cliente
    val tipoCliente = TipoCliente.VIP

    // 4) Crear reserva y calcular resumen
    val reserva = Reserva(seleccion, tipoCliente)
    val resumen = calcularResumen(reserva)

    println("\n--- Resumen de Reserva ---")
    println("Subtotal: $${"%.2f".format(resumen.subtotal)}")
    println("Descuento (${tipoCliente}): $${"%.2f".format(resumen.descuento)}")
    println("Impuestos (19%): $${"%.2f".format(resumen.impuestos)}")
    println("Total: $${"%.2f".format(resumen.total)}")

    // 5) Procesar reserva (simula 3 segundos de espera)
    println("\nProcesando reserva...")
    val estado = procesarReserva(reserva)
    println("Estado de la reserva: $estado")

    // 6) Generar reporte de ventas
    val reporte = generarReporteIngresos(seleccion)
    println("\n--- Reporte de Ventas ---")
    println("Ingresos totales: $${"%.2f".format(reporte.totalIngresos)}")
    println("Ingresos nacionales: $${"%.2f".format(reporte.ingresosNacionales)}")
    println("Ingresos internacionales: $${"%.2f".format(reporte.ingresosInternacionales)}")
    println("Viajes cortos (<=3 días): ${reporte.viajesCortos.joinToString()}")
}