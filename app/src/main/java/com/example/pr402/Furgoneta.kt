package com.example.pr402


// Hereda de Vehiculo
class Furgoneta: Vehiculo {
    // Atributos de la clase
    var carga: Int

    // Constructor
    constructor(ruedas: Int, motor: Int, asientos: Int, color: String, modelo: String, tipo: String, carga: Int) : super(ruedas, motor, asientos, color, modelo, tipo) {
        this.carga = carga
    }

    // toString
    override fun toString(): String {
        return "Furgoneta(carga=$carga)"
    }
}
