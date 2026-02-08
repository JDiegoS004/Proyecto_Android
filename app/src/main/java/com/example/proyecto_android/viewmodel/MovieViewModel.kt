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

    //Lista de peliculas que se muestran en la pantalla Home
    val peliculas = MutableStateFlow<List<MovieEntity>>(emptyList())

    //Indicador de cuando la app está cargando datos
    val cargando = MutableStateFlow(false)
    //Mensaje de error si la aplicación falla
    val error = MutableStateFlow<String?>(null)

    //Metodo usado para añadir una nueva película desde la API, tanto a la lista como a la base de datos
    fun agregarPelicula() {
        viewModelScope.launch {
            cargando.value = true
            error.value = null
            try {
                val peliculaNueva = repositorio.getMovies().firstOrNull { apiMovie ->
                    peliculas.value.none { it.id == apiMovie.id }
                }
                peliculaNueva?.let {
                    repositorio.insertarPelicula(it) //Guarda en la base de datos
                    peliculas.value = peliculas.value + it //Actualiza la lista
                }
            } catch (e: Exception) {
                e.printStackTrace()
                error.value = "Error: ${e.message}" //Muestra un mensaje de error en caso de fallar
            } finally {
                cargando.value = false
            }
        }
    }
    //Metodo usado para borrar una pelicula de la lista y de la base de datos
    fun borrarPelicula(pelicula: MovieEntity) {
        viewModelScope.launch {
            repositorio.eliminarPelicula(pelicula)
            peliculas.value = peliculas.value - pelicula
        }
    }
}
