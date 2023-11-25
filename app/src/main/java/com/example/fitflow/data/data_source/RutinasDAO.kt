package com.example.fitflow.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.fitflow.dominio.modelos.Rutina
import kotlinx.coroutines.flow.Flow

@Dao
interface RutinasDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(rutina: Rutina)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun actualizar(rutina: Rutina)

    @Query("SELECT * FROM rutinas")
    fun obtenerTodasRutinas(): Flow<List<Rutina>>

    @Query("SELECT * FROM rutinas WHERE id = :id")
    fun obtenerRutinaPorId(id: Int): Flow<Rutina>

    @Query("DELETE FROM rutinas WHERE id = :id")
    suspend fun eliminarRutinaPorId(id: Int)

}