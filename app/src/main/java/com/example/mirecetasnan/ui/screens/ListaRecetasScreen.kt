package com.example.mirecetasnan.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.mirecetasnan.viewmodel.RecetaViewModel

@Composable
fun ListaRecetasScreen(
    viewModel: RecetaViewModel = viewModel()
) {
    val recetas = viewModel.recetas.collectAsState().value
    val cargando = viewModel.cargando.collectAsState().value
    val error = viewModel.error.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.cargarRecetas()
    }

    when {
        cargando -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()

                Text(
                    text = "Cargando recetas...",
                    modifier = Modifier.padding(top = 12.dp)
                )
            }
        }

        error != null -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = error,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        else -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(recetas) { receta ->
                    Card {
                        AsyncImage(
                            model = receta.image,
                            contentDescription = receta.nombre,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = receta.nombre,
                                style = MaterialTheme.typography.titleLarge
                            )

                            Text(
                                text = receta.descripcion,
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Button(
                                onClick = {
                                    viewModel.cambiarFavorito(receta.id)
                                }
                            ) {
                                Text(
                                    text = if (receta.favorita) {
                                        "Quitar de favoritos"
                                    } else {
                                        "Agregar a favoritos"
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
