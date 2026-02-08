package com.example.proyecto_android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_android.data.local.entity.MovieEntity
import com.example.proyecto_android.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repositorio: MovieRepository
) : ViewModel() {

    val peliculas = MutableStateFlow<List<MovieEntity>>(emptyList())
    val cargando = MutableStateFlow(false)
    val error = MutableStateFlow<String?>(null)

    fun agregarPelicula() {
        viewModelScope.launch {
            cargando.value = true
            error.value = null
            try {
                val peliculaNueva = repositorio.getMovies().firstOrNull { apiMovie ->
                    peliculas.value.none { it.id == apiMovie.id }
                }
                peliculaNueva?.let {
                    repositorio.insertarPelicula(it)
                    peliculas.value = peliculas.value + it
                }
            } catch (e: Exception) {
                e.printStackTrace()
                error.value = "Error: ${e.message}"
            } finally {
                cargando.value = false
            }
        }
    }
    fun borrarPelicula(pelicula: MovieEntity) {
        viewModelScope.launch {
            repositorio.eliminarPelicula(pelicula)
            peliculas.value = peliculas.value - pelicula
        }
    }
}
