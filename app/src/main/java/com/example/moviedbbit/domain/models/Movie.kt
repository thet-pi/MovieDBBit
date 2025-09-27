package com.example.moviedbbit.domain.models

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterUrl: String?,
    val backdropUrl: String?,
    val releaseDate: String,
    val rating: Double,
    val voteCount: Int,
    val genres: List<Genre>,
    val popularity: Double
)

data class MovieDetails(
    val id: Int,
    val title: String,
    val overview: String,
    val posterUrl: String?,
    val backdropUrl: String?,
    val releaseDate: String,
    val rating: Double,
    val voteCount: Int,
    val genres: List<Genre>,
    val runtime: Int?,
    val status: String,
    val tagline: String?,
    val cast: List<CastMember>,
    val videos: List<Video>,
    val similarMovies: List<Movie>
)

data class Genre(
    val id: Int,
    val name: String
)

data class CastMember(
    val id: Int,
    val name: String,
    val character: String,
    val profileUrl: String?
)

data class Video(
    val id: String,
    val key: String,
    val name: String,
    val site: String,
    val type: String
)