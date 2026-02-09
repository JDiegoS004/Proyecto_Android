package com.example.proyecto_android.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "peliculas")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val poster_path: String,
    val release_date: String,
    val overview: String,
    val nota: String? = null
)

