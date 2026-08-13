package com.example.mirecetasnan.data

import android.content.Context
import kotlinx.coroutines.flow.Flow

class RecetaRepository(context: Context) {

    private val api = RetrofitInstance.api

    private val dao = RecetaDatabase
        .getDatabase(context)
        .recetaDao()

    suspend fun obtenerRecetas(): List<Receta> {
        return api.obtenerRecetas().recipes
    }

    fun obtenerRecetasLocales(): Flow<List<Receta>> {
        return dao.obtenerRecetas()
    }

    suspend fun guardarRecetas(recetas: List<Receta>) {
        dao.insertarRecetas(recetas)
        suspend fun cambiarFavorito(recetaId: Int) {
            dao.cambiarFavorito(recetaId)
        }
    }
}
