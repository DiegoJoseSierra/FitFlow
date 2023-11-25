package com.example.fitflow.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fitflow.di.ActividadListTypeConverter
import com.example.fitflow.di.RutinaCompletadaTypeConverter
import com.example.fitflow.dominio.modelos.Actividad
import com.example.fitflow.dominio.modelos.Historial
import com.example.fitflow.dominio.modelos.Rutina

@Database(
    entities = [
        Actividad::class,
        Rutina::class,
        Historial::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    ActividadListTypeConverter::class,
    RutinaCompletadaTypeConverter::class
)
abstract class FitFlowDB : RoomDatabase() {
    abstract val actividadesDAO: ActividadesDAO
    abstract val rutinasDAO: RutinasDAO
    abstract val historialDAO: HistorialDAO

    companion object {
        const val DATABASE_NAME = "fitflow_db"
    }
}