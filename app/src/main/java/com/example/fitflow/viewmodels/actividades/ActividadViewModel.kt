package com.example.fitflow.viewmodels.actividades

import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitflow.dominio.modelos.Actividad
import com.example.fitflow.dominio.repositorio.ActividadesRepositorio
import com.example.fitflow.estados.EstadoDeDialogo
import com.example.fitflow.estados.EstadoDeTexto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActividadViewModel @Inject constructor(
    private val actividadesRepositorio: ActividadesRepositorio,
) : ViewModel() {
    private val _actividades = mutableStateOf(listOf<Actividad>())
    val actividades: State<List<Actividad>> = _actividades

    private val _nombre = mutableStateOf(
        EstadoDeTexto(
            placeholder = "Nombre de la actividad",
        )
    )
    val nombre: State<EstadoDeTexto> = _nombre

    private val _duracion = mutableStateOf(
        EstadoDeTexto(
            placeholder = "Duración de la actividad",
        )
    )
    val duracion: State<EstadoDeTexto> = _duracion

    private val _descripcion = mutableStateOf(
        EstadoDeTexto(
            placeholder = "Descripción de la actividad",
        )
    )
    val descripcion: State<EstadoDeTexto> = _descripcion

    private val _dialogoVisible = mutableStateOf(EstadoDeDialogo())
    val dialogoVisible: State<EstadoDeDialogo> = _dialogoVisible

    init {
        viewModelScope.launch {
            actividadesRepositorio.obtenerTodasActividades().collect { actividades ->
                _actividades.value = actividades
            }
        }
    }

    fun onEvento(evento: ActividadEvento) {
        when (evento) {
            is ActividadEvento.IngresarNombre -> {
                _nombre.value = nombre.value.copy(
                    valor = evento.valor
                )
            }

            is ActividadEvento.IngresarDuracion -> {
                _duracion.value = duracion.value.copy(
                    valor = evento.valor.toString()
                )
            }

            is ActividadEvento.IngresarDescripcion -> {
                _descripcion.value = descripcion.value.copy(
                    valor = evento.valor
                )
            }

            is ActividadEvento.AbrirDialogo -> {
                _dialogoVisible.value = dialogoVisible.value.copy(
                    visible = true
                )
            }

            is ActividadEvento.CerrarDialogo -> {
                _dialogoVisible.value = dialogoVisible.value.copy(
                    visible = false
                )
            }

            is ActividadEvento.GuardarActividad -> {
                val nombre = _nombre.value.valor
                val duracion = _duracion.value.valor.toInt()
                val descripcion = _descripcion.value.valor

                if (nombre.isEmpty() || duracion == 0 || descripcion.isEmpty()) {
                    Toast.makeText(
                        evento.contexto,
                        "Por favor, llena todos los campos",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }

                val actividad = Actividad(
                    nombre = nombre,
                    duracion = duracion,
                    descripcion = descripcion
                )
                viewModelScope.launch {
                    actividadesRepositorio.insertar(actividad)
                    Toast.makeText(
                        evento.contexto,
                        "Actividad guardada",
                        Toast.LENGTH_SHORT
                    ).show()
                    _dialogoVisible.value = dialogoVisible.value.copy(
                        visible = false
                    )
                }
            }
        }
    }

    fun obtenerActividades(): Flow<List<Actividad>> = flow {
        actividadesRepositorio.obtenerTodasActividades().collect {
            emit(it)
        }
    }
}
