package com.example.fitflow.viewmodels.rutina

import android.content.Context
import com.example.fitflow.dominio.modelos.Actividad

sealed class RutinaEvento {
    data class IngresarNombre(val valor: String) : RutinaEvento()
    data class IngresarDuracion(val valor: Int) : RutinaEvento()
    data class IngresarActividades(val valor: List<Actividad>) : RutinaEvento()

    object AbrirDialogo : RutinaEvento()
    object CerrarDialogo : RutinaEvento()

    data class GuardarRutina(val contexto: Context) : RutinaEvento()

    object MostrarListaDeActividades : RutinaEvento()
    object OcultarListaDeActividades : RutinaEvento()
}