package com.example.mirecetasnan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mirecetasnan.ui.theme.MiRecetasNanTheme
import com.example.mirecetasnan.viewmodel.RecetaViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiRecetasNanTheme {
                val viewModel: RecetaViewModel = viewModel()
                NavGraph(viewModel = viewModel)
            }
        }
    }
}
