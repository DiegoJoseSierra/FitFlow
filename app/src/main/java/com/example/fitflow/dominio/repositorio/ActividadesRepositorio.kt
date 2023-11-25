package com.example.fitflow.dominio.repositorio

import com.example.fitflow.dominio.modelos.Actividad
import kotlinx.coroutines.flow.Flow

interface ActividadesRepositorio {
    suspend fun insertar(actividad: Actividad)
    suspend fun actualizar(actividad: Actividad)
    suspend fun obtenerTodasActividades(): Flow<List<Actividad>>
    suspend fun obtenerActividadPorId(id:Int): Flow<Actividad>
    suspend fun eliminarActividadPorId(id:Int)
}