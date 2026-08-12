package com.example.mirecetasnan.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mirecetasnan.data.Receta

class RecetaViewModel : ViewModel() {

    val recetas = listOf(
        Receta(
            id = 1,
            nombre = "Arroz con pollo",
            descripcion = "Receta popular",
            ingredientes = "Arroz, pollo, verduras, salsa china, sal al gusto",
            preparacion = "Cocinar todos los ingredientes, arroz y pollo. Sofreír las verduras con un poco de salsa china."
        )
    )
}