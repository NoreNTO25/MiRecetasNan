package com.example.mirecetasnan.data

import android.content.Context
import kotlinx.coroutines.flow.Flow

class RecetaRepository(context: Context) {
    private val api = RetrofitInstance.api
    private val dao = RecetaDatabase.getDatabase(context).recetaDao()

        suspend fun fetchRecetasFromTheMealDB(): Result<List<Receta>> {
        return try {
            // 🔥 CAMBIA "Dessert" por la categoría que quieras
            // "Beef", "Chicken", "Dessert", "Vegetarian", "Seafood", "Pasta"
            val categoriaResponse = api.obtenerRecetasPorCategoria("Dessert")
            val meals = categoriaResponse.meals ?: emptyList()

            val recetas = mutableListOf<Receta>()

            // 🔥 SOLO TOMAMOS LAS PRIMERAS 2 RECETAS
            val primerasDosMeals = meals.take(2)

            for (meal in primerasDosMeals) {
                val detalleResponse = api.obtenerRecetaPorId(meal.idMeal)
                val detalle = detalleResponse.meals?.firstOrNull()

                if (detalle != null) {
                    // Convertir ingredientes a lista
                    val ingredientes = listOf(
                        detalle.strIngredient1,
                        detalle.strIngredient2,
                        detalle.strIngredient3,
                        detalle.strIngredient4,
                        detalle.strIngredient5,
                        detalle.strIngredient6,
                        detalle.strIngredient7,
                        detalle.strIngredient8,
                        detalle.strIngredient9,
                        detalle.strIngredient10,
                        detalle.strIngredient11,
                        detalle.strIngredient12,
                        detalle.strIngredient13,
                        detalle.strIngredient14,
                        detalle.strIngredient15,
                        detalle.strIngredient16,
                        detalle.strIngredient17,
                        detalle.strIngredient18,
                        detalle.strIngredient19,
                        detalle.strIngredient20
                    ).filterNotNull()
                        .filter { it.isNotEmpty() }
                        .joinToString(", ")

                    // Crear Receta
                    recetas.add(
                        Receta(
                            id = detalle.idMeal.toIntOrNull() ?: 0,
                            nombre = detalle.strMeal ?: "Sin nombre",
                            descripcion = "Categoría: ${detalle.strCategory ?: "Desconocida"}\nOrigen: ${detalle.strArea ?: "Desconocido"}",
                            ingredientes = ingredientes,
                            preparacion = detalle.strInstructions ?: "Sin instrucciones",
                            favorita = false,
                            image = detalle.strMealThumb ?: ""
                        )
                    )
                }
            }

            // Guardar en Room
            if (recetas.isNotEmpty()) {
                dao.insertarRecetas(recetas)
            }

            Result.success(recetas)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun obtenerRecetasLocales(): Flow<List<Receta>> {
        return dao.obtenerRecetas()
    }

        suspend fun obtenerRecetaPorId(id: Int): Receta? {
        return dao.getRecetaById(id)
    }


    suspend fun insertarRecetas(recetas: List<Receta>) {
        dao.insertarRecetas(recetas)
    }


    suspend fun cambiarFavorito(recetaId: Int) {
        dao.cambiarFavorito(recetaId)
    }

        suspend fun eliminarTodas() {
        dao.eliminarTodas()
    }
}
