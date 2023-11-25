package com.example.fitflow.data.repositorio

import com.example.fitflow.data.data_source.RutinasDAO
import com.example.fitflow.dominio.modelos.Rutina
import com.example.fitflow.dominio.repositorio.RutinasRepositorio
import kotlinx.coroutines.flow.Flow

class RutinasRepositorioImpl (
    private val dao: RutinasDAO
) : RutinasRepositorio {
    override suspend fun insertar(rutina: Rutina) {
        dao.insertar(rutina)
    }

    override suspend fun actualizar(rutina: Rutina) {
        dao.actualizar(rutina)
    }

    override suspend fun obtenerTodasRutinas(): Flow<List<Rutina>> {
        return dao.obtenerTodasRutinas()
    }

    override suspend fun obtenerRutinaPorId(id: Int): Flow<Rutina> {
        return dao.obtenerRutinaPorId(id)
    }

    override suspend fun eliminarRutinaPorId(id: Int) {
        dao.eliminarRutinaPorId(id)
    }
}