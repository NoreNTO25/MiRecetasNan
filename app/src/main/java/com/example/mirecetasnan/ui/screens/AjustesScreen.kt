package com.example.mirecetasnan.ui.screens

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.mirecetasnan.data.UserPreferences
import kotlinx.coroutines.launch

@Composable
fun AjustesScreen(navController: NavHostController) {

    val context = LocalContext.current

    val userPreferences = remember {
        UserPreferences(context)
    }

    val modoOscuro by userPreferences.modoOscuro
        .collectAsState(initial = false)

    val scope = rememberCoroutineScope()

    val camaraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        // La cámara se abrió correctamente.
    }

    val permisoCamara = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { concedido ->

        if (concedido) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            camaraLauncher.launch(intent)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {

        Text(
            text = "Ajustes"
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Modo oscuro",
                modifier = Modifier.weight(1f)
            )

            Switch(
                checked = modoOscuro,
                onCheckedChange = { activado ->
                    scope.launch {
                        userPreferences.guardarModoOscuro(activado)
                    }
                }
            )
        }

        Button(
            onClick = {

                val permiso = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CAMERA
                )

                if (permiso == PackageManager.PERMISSION_GRANTED) {

                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    camaraLauncher.launch(intent)

                } else {

                    permisoCamara.launch(Manifest.permission.CAMERA)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        ) {
            Text("📷 Tomar foto de receta")
        }
    }
}
