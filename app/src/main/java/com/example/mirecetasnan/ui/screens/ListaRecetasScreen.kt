package com.example.mirecetasnan

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.mirecetasnan.viewmodel.RecetaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaRecetasScreen(
    navController: NavController,
    viewModel: RecetaViewModel
) {
    LaunchedEffect(Unit) {
        viewModel.cargarRecetas()
    }

    val recetas by viewModel.recetas.collectAsState()
    val cargando by viewModel.cargando.collectAsState()
    val error by viewModel.error.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Recetas") },
                actions = {
                    // 🔥 BOTÓN DE AJUSTES
                    IconButton(onClick = {
                        navController.navigate("ajustes")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Ajustes"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        when {
            cargando && recetas.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Text("Cargando recetas...", modifier = Modifier.padding(top = 8.dp))
                    }
                }
            }

            error != null && recetas.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("❌ ${error!!}", style = MaterialTheme.typography.titleLarge)
                        Button(
                            onClick = { viewModel.cargarRecetas() },
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Text("Reintentar")
                        }
                    }
                }
            }

            recetas.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("📭 No hay recetas", style = MaterialTheme.typography.titleLarge)
                        Button(
                            onClick = { viewModel.cargarRecetas() },
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Text("Cargar recetas")
                        }
                    }
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(recetas, key = { it.id }) { receta ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column {
                                if (receta.image.isNotEmpty()) {
                                    AsyncImage(
                                        model = receta.image,
                                        contentDescription = receta.nombre,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(180.dp)
                                            .clickable {
                                                navController.navigate("detalle/${receta.id}")
                                            }
                                    )
                                }

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                        .clickable {
                                            navController.navigate("detalle/${receta.id}")
                                        }
                                ) {
                                    Text(
                                        text = receta.nombre,
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                    Text(
                                        text = receta.descripcion,
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                }

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 8.dp),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    Button(
                                        onClick = { viewModel.toggleFavorita(receta) }
                                    ) {
                                        Text(
                                            if (receta.favorita) "★ Quitar de favoritos" else "☆ Agregar a favoritos"
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}