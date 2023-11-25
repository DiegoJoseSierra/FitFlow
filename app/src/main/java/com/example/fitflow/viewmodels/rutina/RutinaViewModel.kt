package com.example.fitflow.viewmodels.rutina

import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitflow.dominio.modelos.Actividad
import com.example.fitflow.dominio.modelos.Rutina
import com.example.fitflow.dominio.repositorio.ActividadesRepositorio
import com.example.fitflow.dominio.repositorio.HistorialRepositorio
import com.example.fitflow.dominio.repositorio.RutinasRepositorio
import com.example.fitflow.estados.EstadoDeDialogo
import com.example.fitflow.estados.EstadoDeDropDown
import com.example.fitflow.estados.EstadoDeTexto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RutinaViewModel @Inject constructor(
    private val rutinaRepositorio: RutinasRepositorio,
    private val historialRepositorio: HistorialRepositorio,
    private val actividadesRepositorio: ActividadesRepositorio,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _rutinas = mutableStateOf(listOf<Rutina>())
    val rutinas: State<List<Rutina>> = _rutinas

    private val _nombre = mutableStateOf(
        EstadoDeTexto(
            placeholder = "Nombre de la rutina",
        )
    )
    val nombre: State<EstadoDeTexto> = _nombre

    private val _duracion = mutableStateOf(
        EstadoDeTexto(
            placeholder = "Duración de la rutina",
        )
    )
    val duracion: State<EstadoDeTexto> = _duracion

    private val _actividades = mutableStateOf(
        EstadoDeDropDown(
            placeholder = "Actividades de la rutina",
        )
    )
    val actividades: State<EstadoDeDropDown> = _actividades

    private val _dialogoVisible = mutableStateOf(EstadoDeDialogo())
    val dialogoVisible: State<EstadoDeDialogo> = _dialogoVisible

    init {
        viewModelScope.launch {
            rutinaRepositorio.obtenerTodasRutinas().collect { rutinas ->
                _rutinas.value = rutinas
            }
        }
    }

    fun onEvento(evento: RutinaEvento) {
        when (evento) {
            is RutinaEvento.IngresarNombre -> {
                _nombre.value = nombre.value.copy(
                    valor = evento.valor
                )
            }

            is RutinaEvento.IngresarDuracion -> {
                _duracion.value = duracion.value.copy(
                    valor = evento.valor.toString()
                )
            }

            is RutinaEvento.IngresarActividades -> {
                _actividades.value = actividades.value.copy(
                    listaDeActividades = evento.valor
                )
            }

            is RutinaEvento.AbrirDialogo -> {
                _dialogoVisible.value = dialogoVisible.value.copy(
                    visible = true
                )
            }

            is RutinaEvento.CerrarDialogo -> {
                _dialogoVisible.value = dialogoVisible.value.copy(
                    visible = false
                )
            }

            is RutinaEvento.GuardarRutina -> {
                val nombre = nombre.value.valor
                val duracion = duracion.value.valor.toInt()
                val actividades = actividades.value.listaDeActividades

                if (nombre.isBlank() || duracion == 0 || actividades.isEmpty()) {
                    Toast.makeText(
                        evento.contexto,
                        "Por favor ingresa un nombre, duración y actividades",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }

                val rutina = Rutina(
                    nombre = nombre,
                    duracion = duracion,
                    actividades = actividades
                )

                viewModelScope.launch {
                    rutinaRepositorio.insertar(rutina)
                    _dialogoVisible.value = dialogoVisible.value.copy(
                        visible = false
                    )
                    limpiarCampos()
                }
            }

            is RutinaEvento.MostrarListaDeActividades -> {
                _actividades.value = _actividades.value.copy(
                    estaExpandido = true
                )
            }

            is RutinaEvento.OcultarListaDeActividades -> {
                _actividades.value = _actividades.value.copy(
                    estaExpandido = false
                )
            }
        }
    }

    fun obtenerActividades(): Flow<List<Actividad>> = flow {
        actividadesRepositorio.obtenerTodasActividades().collect {
            emit(it)
        }
    }

    fun limpiarCampos() {
        _nombre.value = nombre.value.copy(
            valor = ""
        )
        _duracion.value = duracion.value.copy(
            valor = ""
        )
        _actividades.value = actividades.value.copy(
            listaDeActividades = listOf()
        )
    }
}
