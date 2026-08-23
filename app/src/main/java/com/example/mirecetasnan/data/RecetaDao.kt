package com.example.mirecetasnan.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow  // ← AGREGADO

@Dao
interface RecetaDao {
    @Query("SELECT * FROM recetas")
    fun obtenerRecetas(): Flow<List<Receta>>  // ← CORREGIDO: Flow para observar

    @Query("SELECT * FROM recetas WHERE id = :id")
    suspend fun getRecetaById(id: Int): Receta?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarRecetas(recetas: List<Receta>)

    @Query("UPDATE recetas SET favorita = NOT favorita WHERE id = :id")
    suspend fun cambiarFavorito(id: Int)

    @Query("DELETE FROM recetas")
    suspend fun eliminarTodas()
}
