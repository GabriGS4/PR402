package com.example.pr402

open class Vehiculo {
    // Atributos Ruedas, motor, numero de asientos, color y modelo
    var ruedas: Int
    var motor: Int
    var asientos: Int
    var color: String
    var modelo: String
    var tipo: String

    // Constructor
    constructor(ruedas: Int, motor: Int, asientos: Int, color: String, modelo: String, tipo: String) {
        this.ruedas = ruedas
        this.motor = motor
        this.asientos = asientos
        this.color = color
        this.modelo = modelo
        this.tipo = tipo
    }

    // toString
    override fun toString(): String {
        return "Vehiculo(tipo=$tipo, ruedas=$ruedas, motor=$motor, asientos=$asientos, color='$color', modelo='$modelo')"
    }
}