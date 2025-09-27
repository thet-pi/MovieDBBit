package com.example.moviedbbit.domain.usecases

import com.example.moviedbbit.domain.models.MovieDetails
import com.example.moviedbbit.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): Result<MovieDetails> {
        return movieRepository.getMovieDetails(movieId)
    }
}