package com.example.proyecto_android.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import android.net.Uri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.proyecto_android.lista_peliculas.FilmItem
import com.example.proyecto_android.utils.hasInternetConnection
import com.example.proyecto_android.viewmodel.MovieViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {

    val viewModel: MovieViewModel = hiltViewModel()
    val peliculas by viewModel.peliculas.collectAsState()
    val cargando by viewModel.cargando.collectAsState()

    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        // Snackbar para mostrar mensajes
        snackbarHost = { SnackbarHost(snackbarHostState) },

        // TopBar con título
        topBar = {
            TopAppBar(
                title = { Text("Your Top Films", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Blue)
            )
        },

        // Botón flotante para agregar nuevas películas
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // Aviso si no hay conexión a internet
                    if (!hasInternetConnection(context)) {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "No hay conexión a internet. Las imágenes pueden no cargarse 📡",
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                    // Llamada al ViewModel para agregar películas nuevas
                    viewModel.agregarPelicula()
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add films")
            }
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            // Indicador mientras se cargan las películas
            when {
                cargando -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                // Mensaje si no hay películas
                peliculas.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No films yet")
                    }
                }

                // Lista de películas
                else -> {
                    LazyColumn {
                        items(peliculas) { movie ->
                            FilmItem(
                                movie = movie,
                                // Borrar película
                                onDeleteClick = { viewModel.borrarPelicula(it) },
                                // Editar nota personal
                                onEditClick = { movieEditada, nuevaNota ->
                                    viewModel.editarNota(movieEditada.id, nuevaNota)
                                },
                                // Navegar a DetailScreen
                                onClick = {
                                    val idEncoded = movie.id.toString()
                                    val titleEncoded = Uri.encode(movie.title)
                                    val posterEncoded = Uri.encode(movie.poster_path)
                                    val overviewEncoded = Uri.encode(movie.overview)
                                    val noteEncoded = Uri.encode(movie.nota ?: "")

                                    navController.navigate(
                                        "movie_detail/$idEncoded/$titleEncoded/$posterEncoded/$overviewEncoded/$noteEncoded"
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
