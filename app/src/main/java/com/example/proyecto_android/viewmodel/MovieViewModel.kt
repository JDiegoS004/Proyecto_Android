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

    // Lista de películas
    val peliculas = MutableStateFlow<List<MovieEntity>>(emptyList())

    // Indicador de carga
    val cargando = MutableStateFlow(false)

    fun agregarPelicula() {
        viewModelScope.launch {
            cargando.value = true
            try {
                val peliculasApi = repositorio.getMovies()
                val peliculaNueva = peliculasApi.firstOrNull { apiMovie ->
                    peliculas.value.none { it.id == apiMovie.id }
                }
                peliculaNueva?.let {
                    repositorio.insertarPelicula(it)
                    peliculas.value = peliculas.value + it
                }
            } catch (e: Exception) {
                e.printStackTrace()
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

    fun editarNota(movieId: Int, nuevaNota: String) {
        val listaActual = peliculas.value.toMutableList()
        val index = listaActual.indexOfFirst { it.id == movieId }

        if (index != -1) {
            val peliculaActualizada = listaActual[index].copy(nota = nuevaNota)
            listaActual[index] = peliculaActualizada
            peliculas.value = listaActual

            viewModelScope.launch {
                repositorio.actualizarPelicula(peliculaActualizada)
            }
        }
    }
}
