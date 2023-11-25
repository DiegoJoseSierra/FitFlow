package com.example.fitflow.viewmodels.rutina

import android.annotation.SuppressLint
import android.os.CountDownTimer
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitflow.dominio.modelos.Actividad
import com.example.fitflow.dominio.modelos.Historial
import com.example.fitflow.dominio.modelos.Rutina
import com.example.fitflow.dominio.modelos.RutinaCompletada
import com.example.fitflow.dominio.repositorio.HistorialRepositorio
import com.example.fitflow.dominio.repositorio.RutinasRepositorio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ManejarRutinaViewModel @Inject constructor(
    private val historialRepositorio: HistorialRepositorio
) : ViewModel() {

    fun finalizarRutina(rutinaCompletada: RutinaCompletada, contadorGeneral: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                historialRepositorio.insertar(
                    Historial(
                        duracion = contadorGeneral,
                        fecha = obtenerFecha(),
                        rutina = rutinaCompletada
                    )
                )
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun obtenerFecha(): String {
        // Obtener la fecha actual
        val fechaActual = System.currentTimeMillis()
        val formato = java.text.SimpleDateFormat("dd-MM-yyyy")
        return formato.format(fechaActual)
    }
}