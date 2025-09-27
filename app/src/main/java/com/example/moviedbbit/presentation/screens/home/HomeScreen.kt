package com.example.moviedbbit.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.moviedbbit.presentation.components.ErrorScreen
import com.example.moviedbbit.presentation.components.LoadingScreen
import com.example.moviedbbit.presentation.components.MovieSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToDetails: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Movie Info",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { paddingValues ->
        when {
            uiState.isLoading && uiState.popularMovies.isEmpty() -> {
                LoadingScreen(
                    modifier = Modifier.padding(paddingValues)
                )
            }
            
            uiState.error != null && uiState.popularMovies.isEmpty() -> {
                ErrorScreen(
                    message = uiState.error ?: "Unknown error",
                    onRetry = viewModel::retry,
                    modifier = Modifier.padding(paddingValues)
                )
            }
            
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Now Playing Movies (Featured Section)
                    if (uiState.nowPlayingMovies.isNotEmpty()) {
                        MovieSection(
                            title = "Now Playing",
                            movies = uiState.nowPlayingMovies,
                            onMovieClick = onNavigateToDetails,
                            isLoading = uiState.isLoading && uiState.nowPlayingMovies.isEmpty()
                        )
                    }
                    
                    // Popular Movies
                    MovieSection(
                        title = "Popular Movies",
                        movies = uiState.popularMovies,
                        onMovieClick = onNavigateToDetails,
                        isLoading = uiState.isLoading && uiState.popularMovies.isEmpty()
                    )
                    
                    // Top Rated Movies
                    MovieSection(
                        title = "Top Rated",
                        movies = uiState.topRatedMovies,
                        onMovieClick = onNavigateToDetails,
                        isLoading = uiState.isLoading && uiState.topRatedMovies.isEmpty()
                    )
                    
                    // Upcoming Movies
                    MovieSection(
                        title = "Upcoming",
                        movies = uiState.upcomingMovies,
                        onMovieClick = onNavigateToDetails,
                        isLoading = uiState.isLoading && uiState.upcomingMovies.isEmpty()
                    )
                    
                    // Bottom spacing
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

    }
}