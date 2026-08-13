package com.example.mirecetasnan.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mirecetasnan.data.Receta
import com.example.mirecetasnan.data.RecetaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecetaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = RecetaRepository(application)

    private val _recetas = MutableStateFlow(
        listOf(
            Receta(
                id = 1,
                nombre = "Arroz con pollo",
                descripcion = "Receta popular",
                ingredientes = "Arroz, pollo, verduras, salsa china, sal al gusto",
                preparacion = "Cocinar todos los ingredientes y sofreír las verduras con un poco de salsa china."
            )
        )
    )

    val recetas: StateFlow<List<Receta>> = _recetas

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun cargarRecetas() {
        viewModelScope.launch {
            _cargando.value = true
            _error.value = null

            try {
                val resultado = repository.obtenerRecetas()

                if (resultado.isNotEmpty()) {
                    _recetas.value = resultado
                    repository.guardarRecetas(resultado)
                }
            } catch (e: Exception) {
                _error.value = "No se pudo conectar con Internet"
            } finally {
                _cargando.value = false
            }
        }
    }
}
