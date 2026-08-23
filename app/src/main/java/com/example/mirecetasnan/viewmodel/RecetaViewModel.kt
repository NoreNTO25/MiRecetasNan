package com.example.mirecetasnan.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mirecetasnan.data.Receta
import com.example.mirecetasnan.data.RecetaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class RecetaViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = RecetaRepository(application)

    private val _recetas = MutableStateFlow<List<Receta>>(emptyList())
    val recetas: StateFlow<List<Receta>> = _recetas.asStateFlow()

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _recetaSeleccionada = MutableStateFlow<Receta?>(null)
    val recetaSeleccionada: StateFlow<Receta?> = _recetaSeleccionada.asStateFlow()

    init {
        cargarRecetas()
    }

    // ============================================================
    // 🔥 FUNCIÓN PRINCIPAL - CARGA RECETAS (CON 2 EN ESPAÑOL)
    // ============================================================
    fun cargarRecetas() {
        viewModelScope.launch {
            _cargando.value = true
            _error.value = null

            try {
                // 🔥 PASO 1: Intentar cargar desde Room (local)
                val recetasLocales = repository.obtenerRecetasLocales().firstOrNull()
                if (!recetasLocales.isNullOrEmpty()) {
                    _recetas.value = recetasLocales
                    _cargando.value = false
                    return@launch
                }

                // 🔥 PASO 2: Intentar cargar desde API (TheMealDB)
                val result = repository.fetchRecetasFromTheMealDB()
                if (result.isSuccess) {
                    repository.obtenerRecetasLocales().collectLatest { recetas ->
                        if (recetas.isNotEmpty()) {
                            _recetas.value = recetas
                            _error.value = null
                        }
                    }
                } else {
                    // 🔥 PASO 3: Si falla API, usar 2 recetas CORTAS EN ESPAÑOL
                    _error.value = "No se pudo conectar con la API. Mostrando recetas de prueba."
                    val recetasPrueba = listOf(
                        Receta(
                            id = 1,
                            nombre = "🍝 Espaguetis a la Boloñesa",
                            descripcion = "Categoría: Carne\nOrigen: Italiano",
                            ingredientes = "Espaguetis, carne molida, tomate, cebolla, ajo, aceite",
                            preparacion = "Cocinar la pasta. Sofreír carne con cebolla y ajo. Agregar tomate y cocinar 20 min. Servir caliente.",
                            favorita = true,
                            image = "https://www.themealdb.com/images/media/meals/1520081755.jpg"
                        ),
                        Receta(
                            id = 2,
                            nombre = "🥘 Estofado de Res",
                            descripcion = "Categoría: Carne\nOrigen: Irlandés",
                            ingredientes = "Carne de res, cebolla, zanahoria, papa, caldo, sal",
                            preparacion = "Dorar la carne. Agregar verduras y caldo. Cocinar 1 hora. Servir caliente.",
                            favorita = false,
                            image = "https://www.themealdb.com/images/media/meals/1520081755.jpg"
                        )
                    )
                    repository.insertarRecetas(recetasPrueba)
                    _recetas.value = recetasPrueba
                }
            } catch (e: Exception) {
                // 🔥 PASO 4: Si hay error, mostrar 2 recetas CORTAS EN ESPAÑOL
                _error.value = "Error: ${e.message}. Mostrando recetas de prueba."
                val recetasPrueba = listOf(
                    Receta(
                        id = 1,
                        nombre = "🍝 Espaguetis a la Boloñesa",
                        descripcion = "Categoría: Carne\nOrigen: Italiano",
                        ingredientes = "Espaguetis, carne molida, tomate, cebolla, ajo, aceite",
                        preparacion = "Cocinar la pasta. Sofreír carne con cebolla y ajo. Agregar tomate y cocinar 20 min. Servir caliente.",
                        favorita = true,
                        image = "https://www.themealdb.com/images/media/meals/1520081755.jpg"
                    ),
                    Receta(
                        id = 2,
                        nombre = "🥘 Estofado de Res",
                        descripcion = "Categoría: Carne\nOrigen: Irlandés",
                        ingredientes = "Carne de res, cebolla, zanahoria, papa, caldo, sal",
                        preparacion = "Dorar la carne. Agregar verduras y caldo. Cocinar 1 hora. Servir caliente.",
                        favorita = false,
                        image = "https://www.themealdb.com/images/media/meals/1520081755.jpg"
                    )
                )
                _recetas.value = recetasPrueba
            } finally {
                _cargando.value = false
            }
        }
    }

    // ============================================================
    // 🔥 CARGA RECETA POR ID
    // ============================================================
    fun cargarRecetaPorId(id: Int) {
        viewModelScope.launch {
            try {
                val receta = repository.obtenerRecetaPorId(id)
                _recetaSeleccionada.value = receta
            } catch (e: Exception) {
                _error.value = "Error al cargar receta: ${e.message}"
            }
        }
    }

    // ============================================================
    // 🔥 CAMBIAR FAVORITO
    // ============================================================
    fun toggleFavorita(receta: Receta) {
        viewModelScope.launch {
            try {
                repository.cambiarFavorito(receta.id)
                _recetas.value = _recetas.value.map {
                    if (it.id == receta.id) it.copy(favorita = !it.favorita)
                    else it
                }
                if (_recetaSeleccionada.value?.id == receta.id) {
                    _recetaSeleccionada.value = _recetaSeleccionada.value?.copy(
                        favorita = !_recetaSeleccionada.value!!.favorita
                    )
                }
            } catch (e: Exception) {
                _error.value = "Error al cambiar favorito: ${e.message}"
            }
        }
    }

       fun recargarRecetas() {
        viewModelScope.launch {
            _cargando.value = true
            _error.value = null

            try {
                val result = repository.fetchRecetasFromTheMealDB()
                if (result.isSuccess) {
                    repository.obtenerRecetasLocales().collectLatest { recetas ->
                        _recetas.value = recetas
                    }
                } else {
                    _error.value = "Error al recargar recetas"
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            } finally {
                _cargando.value = false
            }
        }
    }
}

