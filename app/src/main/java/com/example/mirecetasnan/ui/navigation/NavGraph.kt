package com.example.mirecetasnan.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mirecetasnan.ui.theme.ui.screens.ListaRecetasScreen
import com.example.mirecetasnan.ui.theme.ui.screens.DetalleRecetaScreen
import com.example.mirecetasnan.ui.theme.ui.screens.AjustesScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController, startDestination = "lista"
    ) {
        composable("lista") {
            ListaRecetasScreen()
        }
        composable("detalle") {
            DetalleRecetaScreen()
        }
        composable("ajustes") {
            AjustesScreen()
        }
    }
}

