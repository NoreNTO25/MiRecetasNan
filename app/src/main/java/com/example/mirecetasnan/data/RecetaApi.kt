package com.example.mirecetasnan.data

import retrofit2.http.GET

interface RecetaApi {

    @GET("recipes")
    suspend fun obtenerRecetas(): RecetasResponse
}
