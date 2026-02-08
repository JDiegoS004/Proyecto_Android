package com.example.proyecto_android.data.repository

import com.example.proyecto_android.data.local.dao.MovieDao
import com.example.proyecto_android.data.local.entity.MovieEntity
import com.example.proyecto_android.data.remote.datasource.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.example.proyecto_android.data.mapper.toEntitySafe


class MovieRepository @Inject constructor(
    private val api: ApiService,
    private val movieDao: MovieDao
) {

    suspend fun getMovies(): List<MovieEntity> {
        val response = api.getMovies("e04b795abd9df23584969a5212bdd02f")
        return response.results.map {
            MovieEntity(
                id = it.id,
                title = it.title,
                poster_path = it.poster_path?.let { path -> "https://image.tmdb.org/t/p/w500$path" } ?: "",
                release_date = it.release_date ?: "",
                overview = it.overview
            )
        }
    }
    suspend fun insertarPelicula(movie: MovieEntity) {
        movieDao.insertarPelicula(movie)
    }

    suspend fun eliminarPelicula(movie: MovieEntity) {
        movieDao.eliminarPelicula(movie)
    }

    fun obtenerTodasLasPeliculas(): Flow<List<MovieEntity>> {
        return movieDao.obtenerTodasLasPeliculas()
    }
}
