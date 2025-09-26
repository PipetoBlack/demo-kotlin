package org.example.model

class ViajeNacional (
    destino: String,
    precioBase: Double,
    duracionDias : Int,
    val temporadaBaja: Boolean
): Viaje(destino, precioBase, duracionDias,"nacional"){
    override fun precioFinal(): Double{
        val base = super.precioFinal()
        val descuento = if (temporadaBaja) 0.10 else 0.0
        return base * (1-descuento)
    }
}