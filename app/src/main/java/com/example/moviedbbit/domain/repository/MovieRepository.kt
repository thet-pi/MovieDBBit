package com.example.moviedbbit.domain.repository

import com.example.moviedbbit.domain.models.Genre
import com.example.moviedbbit.domain.models.Movie
import com.example.moviedbbit.domain.models.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    
    suspend fun getPopularMovies(page: Int = 1): Result<List<Movie>>
    
    suspend fun getTopRatedMovies(page: Int = 1): Result<List<Movie>>
    
    suspend fun getUpcomingMovies(page: Int = 1): Result<List<Movie>>
    
    suspend fun getNowPlayingMovies(page: Int = 1): Result<List<Movie>>
    
    suspend fun getMovieDetails(movieId: Int): Result<MovieDetails>
    
    suspend fun searchMovies(query: String, page: Int = 1): Result<List<Movie>>
    
    suspend fun getGenres(): Result<List<Genre>>
    
    // Favorites
    fun getFavoriteMovies(): Flow<List<Movie>>
    
    suspend fun addToFavorites(movie: Movie)
    
    suspend fun removeFromFavorites(movieId: Int)
    
    fun isFavorite(movieId: Int): Flow<Boolean>
}