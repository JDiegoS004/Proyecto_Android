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

        composable("login") {
            LoginScreen(navController)
        }

        composable("home") {
            HomeScreen(navController)
        }

        composable(
            route = "movie_detail/{movieId}/{movieTitle}/{moviePoster}/{movieOverview}",
            arguments = listOf(
                navArgument("movieId") { type = NavType.StringType },
                navArgument("movieTitle") { type = NavType.StringType },
                navArgument("moviePoster") { type = NavType.StringType },
                navArgument("movieOverview") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val id = backStackEntry.arguments?.getString("movieId")?.toIntOrNull()
            val title = backStackEntry.arguments?.getString("movieTitle")?.let { Uri.decode(it) }
            val poster = backStackEntry.arguments?.getString("moviePoster")?.let { Uri.decode(it) }
            val overview = backStackEntry.arguments?.getString("movieOverview")?.let { Uri.decode(it) }

            DetailScreen(
                navController = navController,
                movieId = id,
                movieTitle = title,
                moviePoster = poster,
                movieOverview = overview
            )
        }
    }
}
