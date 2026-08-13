package com.example.mirecetasnan.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recetas")
data class Receta(
    @PrimaryKey
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val ingredientes: String,
    val preparacion: String,
    val favorita: Boolean= false
)