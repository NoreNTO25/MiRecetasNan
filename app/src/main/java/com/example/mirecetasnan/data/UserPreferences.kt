package com.example.mirecetasnan.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_preferences")

class UserPreferences(private val context: Context) {

    private val modoOscuroKey = booleanPreferencesKey("modo_oscuro")

    val modoOscuro: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[modoOscuroKey] ?: false
        }

    suspend fun guardarModoOscuro(activado: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[modoOscuroKey] = activado
        }
    }
}
