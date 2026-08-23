package com.example.mirecetasnan.data

// ← TU CÓDIGO ORIGINAL
data class RecetaApiModel(
    val id: Int,
    val name: String,
    val description: String,
    val ingredients: List<String>,
    val instructions: List<String>,
    val image: String
)

data class CategoriaResponse(
    val meals: List<MealSummary>?
)

data class MealSummary(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
)

// Respuesta del detalle de una receta
data class RecetaDetalleResponse(
    val meals: List<MealDetail>?
)

data class MealDetail(
    val idMeal: String,
    val strMeal: String,
    val strCategory: String?,
    val strArea: String?,
    val strInstructions: String?,
    val strMealThumb: String?,
    val strIngredient1: String?,
    val strIngredient2: String?,
    val strIngredient3: String?,
    val strIngredient4: String?,
    val strIngredient5: String?,
    val strIngredient6: String?,
    val strIngredient7: String?,
    val strIngredient8: String?,
    val strIngredient9: String?,
    val strIngredient10: String?,
    val strIngredient11: String?,
    val strIngredient12: String?,
    val strIngredient13: String?,
    val strIngredient14: String?,
    val strIngredient15: String?,
    val strIngredient16: String?,
    val strIngredient17: String?,
    val strIngredient18: String?,
    val strIngredient19: String?,
    val strIngredient20: String?
)
