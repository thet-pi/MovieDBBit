package com.example.moviedbbit.presentation.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedbbit.domain.models.Movie
import com.example.moviedbbit.domain.usecases.GetFavoriteMoviesUseCase
import com.example.moviedbbit.domain.usecases.RemoveFromFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            getFavoriteMoviesUseCase().collect { movies ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    favoriteMovies = movies
                )
            }
        }
    }

    fun removeFromFavorites(movieId: Int) {
        viewModelScope.launch {
            removeFromFavoritesUseCase(movieId)
        }
    }
}

data class FavoritesUiState(
    val isLoading: Boolean = false,
    val favoriteMovies: List<Movie> = emptyList()
)