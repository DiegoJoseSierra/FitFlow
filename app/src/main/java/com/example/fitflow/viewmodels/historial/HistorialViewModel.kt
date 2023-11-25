package com.example.fitflow.viewmodels.historial

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitflow.dominio.modelos.Actividad
import com.example.fitflow.dominio.modelos.Historial
import com.example.fitflow.dominio.repositorio.HistorialRepositorio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HistorialViewModel @Inject constructor(
    private val historialRepositorio: HistorialRepositorio
) : ViewModel() {
    private val _historial = mutableStateOf(listOf<Historial>())
    val historial: State<List<Historial>> = _historial

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                historialRepositorio.obtenerHistorial().collect { historial ->
                    _historial.value = historial
                }
            }
        }
    }
}