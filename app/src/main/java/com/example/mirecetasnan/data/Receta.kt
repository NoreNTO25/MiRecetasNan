package com.example.mirecetasnan.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recetas")
data class Receta(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,  // ← CORREGIDO: 0 para auto-generar
    val nombre: String = "",
    val descripcion: String = "",
    val ingredientes: String = "",
    val preparacion: String = "",
    val favorita: Boolean = false,
    val image: String = ""
)
