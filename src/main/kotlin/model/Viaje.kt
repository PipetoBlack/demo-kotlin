package org.example.model

open class Viaje (
    val destino: String,
    val precioBase: Double,
    val duracionDias: Int,
    val categoria: String
){
    open fun precioFinal(): Double{
        require(precioBase > 0 ){"El precio debe ser mayor a 0"}
        require(duracionDias > 0 ){"La duracion debe ser mayor a 0"}
        return precioBase
    }
    override fun toString(): String = "$destino($categoria)- $duracionDias - $precioBase"
}