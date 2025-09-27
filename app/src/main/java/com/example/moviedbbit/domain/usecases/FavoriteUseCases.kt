package com.example.moviedbbit.domain.usecases

import com.example.moviedbbit.domain.models.Movie
import com.example.moviedbbit.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(): Flow<List<Movie>> {
        return movieRepository.getFavoriteMovies()
    }
}

class AddToFavoritesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movie: Movie) {
        movieRepository.addToFavorites(movie)
    }
}

class RemoveFromFavoritesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int) {
        movieRepository.removeFromFavorites(movieId)
    }
}

class IsFavoriteUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(movieId: Int): Flow<Boolean> {
        return movieRepository.isFavorite(movieId)
    }
}