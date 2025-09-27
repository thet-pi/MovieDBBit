package com.example.moviedbbit.domain.usecases

import com.example.moviedbbit.domain.models.Movie
import com.example.moviedbbit.domain.repository.MovieRepository
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(page: Int = 1): Result<List<Movie>> {
        return movieRepository.getPopularMovies(page)
    }
}

class GetTopRatedMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(page: Int = 1): Result<List<Movie>> {
        return movieRepository.getTopRatedMovies(page)
    }
}

class GetUpcomingMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(page: Int = 1): Result<List<Movie>> {
        return movieRepository.getUpcomingMovies(page)
    }
}

class GetNowPlayingMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(page: Int = 1): Result<List<Movie>> {
        return movieRepository.getNowPlayingMovies(page)
    }
}