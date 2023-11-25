package com.example.fitflow.viewmodels.actividades

import android.content.Context
import com.example.fitflow.dominio.modelos.Actividad

sealed class ActividadEvento {
    data class IngresarNombre(val valor: String) : ActividadEvento()
    data class IngresarDuracion(val valor: Int) : ActividadEvento()
    data class IngresarDescripcion(val valor: String) : ActividadEvento()

    object AbrirDialogo : ActividadEvento()
    object CerrarDialogo : ActividadEvento()

    data class GuardarActividad(val contexto: Context) : ActividadEvento()
}