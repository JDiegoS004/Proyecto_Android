package com.example.proyecto_android.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.proyecto_android.ui.theme.screens.LoginScreen
import com.example.proyecto_android.ui.theme.screens.HomeScreen
import com.example.proyecto_android.ui.theme.screens.DetailScreen

@Composable
fun NavigationController() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        //Pantalla de login
        composable("login") {
            LoginScreen(navController)
        }

        //Pantalla Principal del programa con la lista de las peliculas
        composable("home") {
            HomeScreen(navController)
        }

        //Pantalla con detalles de la película
        composable(
            route = "movie_detail/{movieId}/{movieTitle}/{moviePoster}/{movieOverview}/{movieNote}",
            arguments = listOf(
                navArgument("movieId") { type = NavType.StringType },
                navArgument("movieTitle") { type = NavType.StringType },
                navArgument("moviePoster") { type = NavType.StringType },
                navArgument("movieOverview") { type = NavType.StringType },
                navArgument("movieNote") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("movieId")?.toIntOrNull()
            val title = backStackEntry.arguments?.getString("movieTitle")?.let { Uri.decode(it) }
            val poster = backStackEntry.arguments?.getString("moviePoster")?.let { Uri.decode(it) }
            val overview = backStackEntry.arguments?.getString("movieOverview")?.let { Uri.decode(it) }
            val note = backStackEntry.arguments?.getString("movieNote")?.let { Uri.decode(it) }

            DetailScreen(
                navController = navController,
                movieId = id,
                movieTitle = title,
                moviePoster = poster,
                movieOverview = overview,
                movieNote = note
            )
        }
    }
}