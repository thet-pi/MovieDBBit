package com.example.moviedbbit.presentation.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedbbit.domain.models.Movie
import com.example.moviedbbit.domain.models.MovieDetails
import com.example.moviedbbit.domain.usecases.AddToFavoritesUseCase
import com.example.moviedbbit.domain.usecases.GetMovieDetailsUseCase
import com.example.moviedbbit.domain.usecases.IsFavoriteUseCase
import com.example.moviedbbit.domain.usecases.RemoveFromFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase,
    private val isFavoriteUseCase: IsFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailsUiState())
    val uiState: StateFlow<MovieDetailsUiState> = _uiState.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    fun loadMovieDetails(movieId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            // Load movie details
            getMovieDetailsUseCase(movieId).fold(
                onSuccess = { movieDetails ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        movieDetails = movieDetails,
                        error = null
                    )
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message ?: "Failed to load movie details"
                    )
                }
            )
            
            // Check if movie is favorite
            isFavoriteUseCase(movieId).collect { favorite ->
                _isFavorite.value = favorite
            }
        }
    }

    fun toggleFavorite() {
        val movieDetails = _uiState.value.movieDetails ?: return
        
        viewModelScope.launch {
            if (_isFavorite.value) {
                removeFromFavoritesUseCase(movieDetails.id)
            } else {
                // Convert MovieDetails to Movie for favorites
                val movie = Movie(
                    id = movieDetails.id,
                    title = movieDetails.title,
                    overview = movieDetails.overview,
                    posterUrl = movieDetails.posterUrl,
                    backdropUrl = movieDetails.backdropUrl,
                    releaseDate = movieDetails.releaseDate,
                    rating = movieDetails.rating,
                    voteCount = movieDetails.voteCount,
                    genres = movieDetails.genres,
                    popularity = 0.0 // Not available in MovieDetails
                )
                addToFavoritesUseCase(movie)
            }
        }
    }

    fun retry(movieId: Int) {
        loadMovieDetails(movieId)
    }
}

data class MovieDetailsUiState(
    val isLoading: Boolean = false,
    val movieDetails: MovieDetails? = null,
    val error: String? = null
)