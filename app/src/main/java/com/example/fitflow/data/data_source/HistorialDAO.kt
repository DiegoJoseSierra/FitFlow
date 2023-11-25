package com.example.fitflow.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fitflow.dominio.modelos.Historial
import com.example.fitflow.navegacion.Pantallas
import kotlinx.coroutines.flow.Flow

@Dao
interface HistorialDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertar(historial: Historial)

    @Query("SELECT * FROM historial")
    fun obtenerHistorial(): Flow<List<Historial>>

    @Query("SELECT * FROM historial WHERE id = :id")
    fun obtenerHistorialPorId(id: Int): Flow<Historial>

    @Query("DELETE FROM historial WHERE id = :id")
    fun eliminar(id: Int)
}