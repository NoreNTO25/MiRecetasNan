package com.example.mirecetasnan.data

import retrofit2.http.GET
import retrofit2.http.Query  // ← AGREGADO

interface RecetaApi {
    // ← TU CÓDIGO ORIGINAL (lo dejo por si acaso)
    @GET("recipes")
    suspend fun obtenerRecetas(): RecetaResponse

       @GET("filter.php")
    suspend fun obtenerRecetasPorCategoria(
        @Query("c") categoria: String = "Beef"
    ): CategoriaResponse

    @GET("lookup.php")
    suspend fun obtenerRecetaPorId(
        @Query("i") id: String
    ): RecetaDetalleResponse
}
