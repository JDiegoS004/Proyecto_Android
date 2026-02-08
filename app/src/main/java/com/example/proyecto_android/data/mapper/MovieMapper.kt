package com.example.proyecto_android.data.mapper

import com.example.proyecto_android.data.local.entity.MovieEntity
import com.example.proyecto_android.data.remote.model.MovieResult

fun MovieResult.toEntitySafe(): MovieEntity {
    return try {
        MovieEntity(
            id = id,
            title = title,
            poster_path = poster_path ?: "",
            release_date = release_date ?: "",
            overview = overview
        )
    } catch (e: Exception) {
        MovieEntity(id = id, title = "Error", poster_path = "", release_date = "", overview = "")
    }
}

