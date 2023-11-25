package com.example.fitflow.dominio.repositorio

import com.example.fitflow.dominio.modelos.Historial
import kotlinx.coroutines.flow.Flow

interface HistorialRepositorio {
    suspend fun obtenerHistorial(): Flow<List<Historial>>
    suspend fun obtenerHistorialPorId(id: Int): Flow<Historial>
    suspend fun insertar(historial: Historial)
    suspend fun eliminar(historial: Historial)
}