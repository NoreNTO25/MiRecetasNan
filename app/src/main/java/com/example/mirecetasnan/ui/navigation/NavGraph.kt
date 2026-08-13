package com.example.mirecetasnan.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mirecetasnan.data.Receta
import com.example.mirecetasnan.ui.screens.AjustesScreen
import com.example.mirecetasnan.ui.screens.DetalleRecetaScreen
import com.example.mirecetasnan.ui.screens.ListaRecetasScreen

@Composable
fun NavGraph() {

    val navController = rememberNavController()

    val receta = Receta(
        id = 1,
        nombre = "Arroz con pollo",
        descripcion = "Receta de toda fiesta",
        ingredientes = "Arroz, pollo, verduras, salsa china y sal al gusto",
        preparacion = "Cocinar todos los ingredientes y sofreír las verduras."
    )

    NavHost(
        navController = navController,
        startDestination = "lista"
    ) {

        composable("lista") {
            ListaRecetasScreen()
        }

        composable("detalle") {
            DetalleRecetaScreen(receta = receta)
        }

        composable("ajustes") {
            AjustesScreen()
        }
    }
}


