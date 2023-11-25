package com.example.fitflow.di

import androidx.room.TypeConverter
import com.example.fitflow.dominio.modelos.Actividad
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ActividadListTypeConverter {
    @TypeConverter
    fun fromActividadList(actividades: List<Actividad>): String {
        val type = object : TypeToken<List<Actividad>>() {}.type
        return Gson().toJson(actividades, type)
    }

    @TypeConverter
    fun toActividadList(actividadesString: String): List<Actividad> {
        val type = object : TypeToken<List<Actividad>>() {}.type
        return Gson().fromJson(actividadesString, type)
    }
}