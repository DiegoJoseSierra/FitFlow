package com.example.fitflow.navegacion

sealed class Pantallas(val ruta: String, val nombre: String) {
    object Rutinas : Pantallas(ruta = "rutinas", "Rutinas")
    object Actividades : Pantallas(ruta = "actividades", "Actividades")
    object Historial : Pantallas(ruta = "historial", "Historial")
}