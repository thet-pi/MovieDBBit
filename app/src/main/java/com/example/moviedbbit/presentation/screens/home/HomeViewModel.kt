package com.example.moviedbbit.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedbbit.domain.models.Movie
import com.example.moviedbbit.domain.usecases.GetNowPlayingMoviesUseCase
import com.example.moviedbbit.domain.usecases.GetPopularMoviesUseCase
import com.example.moviedbbit.domain.usecases.GetTopRatedMoviesUseCase
import com.example.moviedbbit.domain.usecases.GetUpcomingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            try {
                // Load all movie categories concurrently
                val popularResult = getPopularMoviesUseCase()
                val topRatedResult = getTopRatedMoviesUseCase()
                val upcomingResult = getUpcomingMoviesUseCase()
                val nowPlayingResult = getNowPlayingMoviesUseCase()

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    popularMovies = popularResult.getOrElse { emptyList() },
                    topRatedMovies = topRatedResult.getOrElse { emptyList() },
                    upcomingMovies = upcomingResult.getOrElse { emptyList() },
                    nowPlayingMovies = nowPlayingResult.getOrElse { emptyList() },
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error occurred"
                )
            }
        }
    }

    fun retry() {
        loadMovies()
    }
}

data class HomeUiState(
    val isLoading: Boolean = false,
    val popularMovies: List<Movie> = emptyList(),
    val topRatedMovies: List<Movie> = emptyList(),
    val upcomingMovies: List<Movie> = emptyList(),
    val nowPlayingMovies: List<Movie> = emptyList(),
    val error: String? = null
)