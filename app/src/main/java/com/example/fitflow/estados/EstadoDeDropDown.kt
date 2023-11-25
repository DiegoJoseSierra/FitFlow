package com.example.fitflow.estados

import com.example.fitflow.dominio.modelos.Actividad


data class EstadoDeDropDown (
    val estaExpandido: Boolean = true,
    val opcionSeleccionada: String = "",
    val listaDeActividades: List<Actividad> = listOf(),
    val placeholder: String = "",
)