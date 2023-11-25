package com.example.fitflow.dominio.repositorio

import com.example.fitflow.dominio.modelos.Rutina
import kotlinx.coroutines.flow.Flow

interface RutinasRepositorio {
    suspend fun insertar(rutina: Rutina)
    suspend fun actualizar(rutina: Rutina)
    suspend fun obtenerTodasRutinas(): Flow<List<Rutina>>
    suspend fun obtenerRutinaPorId(id:Int): Flow<Rutina>
    suspend fun eliminarRutinaPorId(id:Int)
}