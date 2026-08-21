package com.example.mirecetasnan.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

import com.example.mirecetasnan.data.Receta

@Composable
fun DetalleRecetaScreen(receta: Receta) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        AsyncImage(
            model = receta.image,
            contentDescription = receta.nombre,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        )


        Text(
            text = receta.nombre,
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            text = receta.descripcion,
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            text = "Ingredientes:",
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            text = receta.ingredientes,
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            text = "Preparación:",
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            text = receta.preparacion,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}
