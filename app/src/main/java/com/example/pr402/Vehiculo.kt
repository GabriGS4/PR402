package com.example.pr402

open class Vehiculo {
    // Atributos Ruedas, motor, numero de asientos, color y modelo
    var ruedas: Int
    var motor: Int
    var asientos: Int
    var color: String
    var modelo: String

    // Constructor
    constructor(ruedas: Int, motor: Int, asientos: Int, color: String, modelo: String) {
        this.ruedas = ruedas
        this.motor = motor
        this.asientos = asientos
        this.color = color
        this.modelo = modelo
    }

    // toString
    override fun toString(): String {
        return "Vehiculo(ruedas=$ruedas, motor=$motor, asientos=$asientos, color='$color', modelo='$modelo')"
    }
}