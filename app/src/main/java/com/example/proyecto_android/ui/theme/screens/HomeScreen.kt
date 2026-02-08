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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.proyecto_android.lista_peliculas.FilmItem
import com.example.proyecto_android.viewmodel.MovieViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {

    val viewModel: MovieViewModel = hiltViewModel()
    val peliculas by viewModel.peliculas.collectAsState()
    val cargando by viewModel.cargando.collectAsState()
    val error by viewModel.error.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Your Top Films", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Blue)
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.agregarPelicula() }) {
                Icon(Icons.Default.Add, contentDescription = "Add films")
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            when {
                cargando -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                peliculas.isEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No films yet")
                    }
                }
                else -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(peliculas) { movie ->
                            FilmItem(
                                movie = movie,
                                onDeleteClick = { viewModel.borrarPelicula(it) },
                                onClick = {
                                    val encode: (String?) -> String = {
                                        URLEncoder.encode(it ?: "", StandardCharsets.UTF_8.toString())
                                            .replace("+", "%20")
                                    }

                                    val id = movie.id
                                    val title = encode(movie.title)
                                    val poster = encode(movie.poster_path)
                                    val overview = encode(movie.overview)

                                    navController.navigate("movie_detail/$id/$title/$poster/$overview")
                                }
                            )
                        }
                    }
                }
            }
            if (!error.isNullOrEmpty()) {
                Text(
                    text = "Error: $error",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.TopCenter).padding(8.dp)
                )
            }
        }
    }
}
