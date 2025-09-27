package com.example.moviedbbit.data.repository

import com.example.moviedbbit.data.api.TmdbApiService
import com.example.moviedbbit.data.database.FavoriteMovieDao
import com.example.moviedbbit.data.database.MovieCacheDao
import com.example.moviedbbit.data.mappers.toDomain
import com.example.moviedbbit.data.mappers.toFavoriteEntity
import com.example.moviedbbit.domain.models.Genre
import com.example.moviedbbit.domain.models.Movie
import com.example.moviedbbit.domain.models.MovieDetails
import com.example.moviedbbit.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val apiService: TmdbApiService,
    private val favoriteMovieDao: FavoriteMovieDao,
    private val movieCacheDao: MovieCacheDao
) : MovieRepository {

    private var cachedGenres: List<Genre>? = null

    override suspend fun getPopularMovies(page: Int): Result<List<Movie>> {
        return try {
            val response = apiService.getPopularMovies(page)
            val genres = getGenresInternal()
            val movies = response.results.map { movieDto ->
                val movieGenres = genres.filter { genre -> 
                    movieDto.genreIds.contains(genre.id) 
                }
                movieDto.toDomain(movieGenres)
            }
            Result.success(movies)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getTopRatedMovies(page: Int): Result<List<Movie>> {
        return try {
            val response = apiService.getTopRatedMovies(page)
            val genres = getGenresInternal()
            val movies = response.results.map { movieDto ->
                val movieGenres = genres.filter { genre -> 
                    movieDto.genreIds.contains(genre.id) 
                }
                movieDto.toDomain(movieGenres)
            }
            Result.success(movies)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUpcomingMovies(page: Int): Result<List<Movie>> {
        return try {
            val response = apiService.getUpcomingMovies(page)
            val genres = getGenresInternal()
            val movies = response.results.map { movieDto ->
                val movieGenres = genres.filter { genre -> 
                    movieDto.genreIds.contains(genre.id) 
                }
                movieDto.toDomain(movieGenres)
            }
            Result.success(movies)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getNowPlayingMovies(page: Int): Result<List<Movie>> {
        return try {
            val response = apiService.getNowPlayingMovies(page)
            val genres = getGenresInternal()
            val movies = response.results.map { movieDto ->
                val movieGenres = genres.filter { genre -> 
                    movieDto.genreIds.contains(genre.id) 
                }
                movieDto.toDomain(movieGenres)
            }
            Result.success(movies)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMovieDetails(movieId: Int): Result<MovieDetails> {
        return try {
            val response = apiService.getMovieDetails(movieId)
            val movieDetails = response.toDomain()
            Result.success(movieDetails)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun searchMovies(query: String, page: Int): Result<List<Movie>> {
        return try {
            val response = apiService.searchMovies(query, page)
            val genres = getGenresInternal()
            val movies = response.results.map { movieDto ->
                val movieGenres = genres.filter { genre -> 
                    movieDto.genreIds.contains(genre.id) 
                }
                movieDto.toDomain(movieGenres)
            }
            Result.success(movies)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getGenres(): Result<List<Genre>> {
        return try {
            if (cachedGenres == null) {
                val response = apiService.getGenres()
                cachedGenres = response.genres.map { it.toDomain() }
            }
            Result.success(cachedGenres!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun getGenresInternal(): List<Genre> {
        return try {
            if (cachedGenres == null) {
                val response = apiService.getGenres()
                cachedGenres = response.genres.map { it.toDomain() }
            }
            cachedGenres!!
        } catch (e: Exception) {
            emptyList()
        }
    }

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return favoriteMovieDao.getAllFavorites().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun addToFavorites(movie: Movie) {
        favoriteMovieDao.insertFavorite(movie.toFavoriteEntity())
    }

    override suspend fun removeFromFavorites(movieId: Int) {
        favoriteMovieDao.deleteFavoriteById(movieId)
    }

    override fun isFavorite(movieId: Int): Flow<Boolean> {
        return favoriteMovieDao.isFavorite(movieId)
    }
}