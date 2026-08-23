package com.example.mirecetasnan

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mirecetasnan.ui.screens.AjustesScreen
import com.example.mirecetasnan.viewmodel.RecetaViewModel

@Composable
fun NavGraph(viewModel: RecetaViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "lista"
    ) {
        // 🏠 Pantalla de lista
        composable(route = "lista") {
            ListaRecetasScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        // 📄 Pantalla de detalle
        composable(
            route = "detalle/{recetaId}",
            arguments = listOf(
                navArgument("recetaId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val recetaId = backStackEntry.arguments?.getInt("recetaId") ?: 0
            viewModel.cargarRecetaPorId(recetaId)
            val receta by viewModel.recetaSeleccionada.collectAsState()
            DetalleRecetaScreen(
                receta = receta,
                navController = navController
            )
        }

        // ⚙️ Pantalla de ajustes
        composable(route = "ajustes") {
            AjustesScreen(
                navController = navController
            )
        }
    }
}
