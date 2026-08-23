package com.example.mirecetasnan.data

// ← TU CÓDIGO ORIGINAL
data class RecetaResponse(
    val recipes: List<RecetaApiModel>
)


data class TheMealDBResponse(
    val meals: List<RecetaApiModel>?
)
