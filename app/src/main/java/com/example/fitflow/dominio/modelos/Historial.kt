package com.example.fitflow.dominio.modelos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "historial")
data class Historial (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val fecha: String,
    val duracion: Int,
    val rutina: RutinaCompletada
)