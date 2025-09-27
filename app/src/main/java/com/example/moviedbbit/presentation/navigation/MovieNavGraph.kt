package com.example.moviedbbit.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.moviedbbit.presentation.screens.details.MovieDetailsScreen
import com.example.moviedbbit.presentation.screens.favorites.FavoritesScreen
import com.example.moviedbbit.presentation.screens.home.HomeScreen
import com.example.moviedbbit.presentation.screens.search.SearchScreen

@Composable
fun MovieNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = hiltViewModel(),
                onNavigateToDetails = { movieId ->
                    navController.navigate(Screen.Details.createRoute(movieId))
                }
            )
        }
        
        composable(Screen.Search.route) {
            SearchScreen(
                viewModel = hiltViewModel(),
                onNavigateToDetails = { movieId ->
                    navController.navigate(Screen.Details.createRoute(movieId))
                }
            )
        }
        
        composable(
            route = Screen.Details.route,
            arguments = listOf(
                navArgument("movieId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: return@composable
            MovieDetailsScreen(
                viewModel = hiltViewModel(),
                movieId = movieId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.Favorites.route) {
            FavoritesScreen(
                viewModel = hiltViewModel(),
                onNavigateToDetails = { movieId ->
                    navController.navigate(Screen.Details.createRoute(movieId))
                }
            )
        }
    }
}