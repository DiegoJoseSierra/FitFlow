package com.example.fitflow.data.repositorio

import com.example.fitflow.data.data_source.HistorialDAO
import com.example.fitflow.dominio.modelos.Historial
import com.example.fitflow.dominio.repositorio.HistorialRepositorio
import kotlinx.coroutines.flow.Flow

class HistorialRepositorioImpl (
    private val dao: HistorialDAO
) : HistorialRepositorio {
    override suspend fun obtenerHistorial(): Flow<List<Historial>> {
        return dao.obtenerHistorial()
    }

    override suspend fun obtenerHistorialPorId(id: Int): Flow<Historial> {
        return dao.obtenerHistorialPorId(id)
    }

    override suspend fun insertar(historial: Historial) {
        dao.insertar(historial)
    }

    override suspend fun eliminar(historial: Historial) {
        dao.eliminar(historial.id)
    }
}