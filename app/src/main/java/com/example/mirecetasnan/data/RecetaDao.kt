package com.example.mirecetasnan.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RecetaDao {

    @Query("SELECT * FROM recetas")
    fun obtenerRecetas(): Flow<List<Receta>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarRecetas(recetas: List<Receta>)
    @Query("UPDATE recetas SET favorita = NOT favorita WHERE id = :recetaId")
    suspend fun cambiarFavorito(recetaId: Int)
}

