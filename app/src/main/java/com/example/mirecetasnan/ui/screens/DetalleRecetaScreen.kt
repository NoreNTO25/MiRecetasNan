package com.example.mirecetasnan

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.mirecetasnan.data.Receta

@Composable
fun DetalleRecetaScreen(
    receta: Receta?,
    navController: NavController
) {
    if (receta == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    // ============================================================
    // 🔥 CONTENIDO CON SCROLL PARA QUE EL BOTÓN SIEMPRE SE VEA
    // ============================================================
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())  // ← ESTO PERMITE SCROLL
            .padding(16.dp)
    ) {
        // 📷 Imagen
        if (receta.image.isNotEmpty()) {
            AsyncImage(
                model = receta.image,
                contentDescription = receta.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            )
        }

        // 📝 Nombre
        Text(
            text = receta.nombre,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )

        // 🏷️ Categoría y Origen
        Text(
            text = receta.descripcion,
            modifier = Modifier.padding(top = 8.dp)
        )

        // ⭐ Favorito
        Text(
            text = if (receta.favorita) "⭐ Favorita" else "☆ No favorita",
            modifier = Modifier.padding(top = 8.dp)
        )

        // 📝 Ingredientes
        Text(
            text = "📝 Ingredientes:",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = receta.ingredientes,
            modifier = Modifier.padding(top = 8.dp)
        )

        // 👨‍🍳 Preparación
        Text(
            text = "👨‍🍳 Preparación:",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = receta.preparacion,
            modifier = Modifier.padding(top = 8.dp)
        )

        // ============================================================
        // 🔥 ESPACIO EXTRA PARA SEPARAR EL BOTÓN
        // ============================================================
        Spacer(modifier = Modifier.height(32.dp))

        // ============================================================
        // 🔙 BOTÓN VOLVER (SIEMPRE VISIBLE)
        // ============================================================
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)  // ← ALTURA FIJA PARA QUE SE VEA BIEN
        ) {
            Text(
                text = "⬅️ Volver",
                fontSize = 18.sp
            )
        }

        // Espacio extra al final
        Spacer(modifier = Modifier.height(16.dp))
    }
}

