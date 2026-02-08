package com.example.proyecto_android.data.mapper

import com.example.proyecto_android.data.local.entity.MovieEntity
import com.example.proyecto_android.data.remote.model.MovieResult

// El mapper pasa los datos de la API a un formato que nuestra app pueda usar y almacenar.
// Lo hemos usado ya que, tras muchos intentos, nuestra app no conseguía recibir bien los datos de la API por lo que,
// buscando información hemos encontrado que la mejor forma de conseguir un correcto funcionamiento y mantener una buena arquitectura es esta.

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

