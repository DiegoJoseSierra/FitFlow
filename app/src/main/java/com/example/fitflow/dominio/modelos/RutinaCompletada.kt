package com.example.fitflow.dominio.modelos

data class RutinaCompletada(
    val id: Int,
    val nombre: String,
    val duracion: Int,
    val actividades: List<Actividad>
)
