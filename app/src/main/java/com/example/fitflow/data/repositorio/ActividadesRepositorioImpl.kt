package com.example.fitflow.data.repositorio

import com.example.fitflow.data.data_source.ActividadesDAO
import com.example.fitflow.dominio.modelos.Actividad
import com.example.fitflow.dominio.repositorio.ActividadesRepositorio
import kotlinx.coroutines.flow.Flow

class ActividadesRepositorioImpl (
    private val dao: ActividadesDAO
) : ActividadesRepositorio {
    override suspend fun insertar(actividad: Actividad) {
        dao.insertarActividad(actividad)
    }

    override suspend fun actualizar(actividad: Actividad) {
        dao.actualizarActividad(actividad)
    }

    override suspend fun obtenerTodasActividades(): Flow<List<Actividad>> {
        return dao.obtenerTodasActividades()
    }

    override suspend fun obtenerActividadPorId(id: Int): Flow<Actividad> {
        return dao.obtenerActividadPorId(id)
    }

    override suspend fun eliminarActividadPorId(id: Int) {
        dao.eliminarActividadPorId(id)
    }

}