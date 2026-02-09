package com.example.proyecto_android.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage

//En esta pantalla monstramos el poster de la pelicula, el id, el nombre y la descripción oficial.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavHostController,
    movieId: Int?,
    movieTitle: String?,
    moviePoster: String?,
    movieOverview: String?,
    movieNote: String?
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Details") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Red),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Text("<")
                    }
                }
            )
        }
    ) { paddingValues ->
        // Aquí se mostrará la información de la película
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)){
            //Poster de la pelicula
            AsyncImage(
                model = moviePoster ?: "",
                contentDescription = movieTitle,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
            Spacer(
                modifier = Modifier.height(16.dp)
            )
            Text(text = "ID: $movieId", fontSize = 16.sp)
            Spacer(
                modifier = Modifier.height(16.dp)
            )
            Text(text = "Title: $movieTitle", fontSize = 20.sp)
            Spacer(
                modifier = Modifier.height(16.dp)
            )
            Text(text = "Description:", fontSize = 20.sp)
            Text(text = movieOverview ?: "")
            Spacer(
                modifier = Modifier.height(16.dp)
            )
            Text(text = "Nota personal:", fontSize = 20.sp)
            Text(text = movieNote ?: "")
        }
    }
}
