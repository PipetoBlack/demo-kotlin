package org.example.model

class ViajeInternacional(
    destino: String,
    precioBase: Double,
    duracionDias : Int,
    val tasaInternacional: Double
): Viaje(destino,precioBase,duracionDias,"Internacional") {
    override fun precioFinal(): Double{
        val base = super.precioFinal()
        require(tasaInternacional >= 0){"La tasa internacional no puede ser negativa"}
    return base * (1+tasaInternacional)
    }
}