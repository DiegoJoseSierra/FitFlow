package com.example.fitflow.di

import androidx.room.TypeConverter
import com.example.fitflow.dominio.modelos.RutinaCompletada
import com.google.gson.Gson

class RutinaCompletadaTypeConverter {
    @TypeConverter
    fun fromRutinaCompletada(rutina: RutinaCompletada): String {
        return Gson().toJson(rutina)
    }

    @TypeConverter
    fun toRutinaCompletada(rutinaString: String): RutinaCompletada {
        return Gson().fromJson(rutinaString, RutinaCompletada::class.java)
    }
}
