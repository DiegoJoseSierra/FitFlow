package com.example.fitflow.dominio.modelos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rutinas")
data class Rutina(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val duracion: Int,
    val actividades: List<Actividad>
)
