package com.example.fitflow.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.fitflow.dominio.modelos.Actividad
import kotlinx.coroutines.flow.Flow

@Dao
interface ActividadesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarActividad(actividad: Actividad)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun actualizarActividad(actividad: Actividad)

    @Query("SELECT * FROM actividades")
    fun obtenerTodasActividades(): Flow<List<Actividad>>

    @Query("SELECT * FROM actividades WHERE id = :id")
    fun obtenerActividadPorId(id: Int): Flow<Actividad>

    @Query("DELETE FROM actividades WHERE id = :id")
    suspend fun eliminarActividadPorId(id: Int)
}