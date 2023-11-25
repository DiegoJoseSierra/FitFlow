package com.example.fitflow.di

import android.app.Application
import androidx.room.Room
import com.example.fitflow.data.data_source.FitFlowDB
import com.example.fitflow.data.repositorio.ActividadesRepositorioImpl
import com.example.fitflow.data.repositorio.HistorialRepositorioImpl
import com.example.fitflow.data.repositorio.RutinasRepositorioImpl
import com.example.fitflow.dominio.repositorio.ActividadesRepositorio
import com.example.fitflow.dominio.repositorio.HistorialRepositorio
import com.example.fitflow.dominio.repositorio.RutinasRepositorio
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FitFlowModulo {
    @Provides
    @Singleton
    fun providesDB(app: Application): FitFlowDB {
        return Room.databaseBuilder(
            app,
            FitFlowDB::class.java,
            FitFlowDB.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providesRepositorioRutina(
        db: FitFlowDB,
    ): RutinasRepositorio = RutinasRepositorioImpl(db.rutinasDAO)

    @Provides
    @Singleton
    fun providesRepositorioActividad(
        db: FitFlowDB,
    ): ActividadesRepositorio = ActividadesRepositorioImpl(db.actividadesDAO)

    @Provides
    @Singleton
    fun providesRepositorioHistorial(
        db: FitFlowDB
    ) : HistorialRepositorio = HistorialRepositorioImpl(db.historialDAO)
}