package com.example.moviedbbit.domain.usecases

import com.example.moviedbbit.domain.models.Movie
import com.example.moviedbbit.domain.repository.MovieRepository
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(query: String, page: Int = 1): Result<List<Movie>> {
        return movieRepository.searchMovies(query, page)
    }
}