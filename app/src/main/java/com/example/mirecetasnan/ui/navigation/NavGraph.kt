package com.example.mirecetasnan.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mirecetasnan.ui.screens.DetalleRecetaScreen
import com.example.mirecetasnan.ui.screens.AjustesScreen
import com.example.mirecetasnan.ui.screens.ListaRecetasScreen


@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController, startDestination = "lista"
    ) {
        composable("lista") {
            `ListaRecetasScreen`()
        }
        composable("detalle") {
            DetalleRecetaScreen()
        }
        composable("ajustes") {
            AjustesScreen()
        }
    }
}

