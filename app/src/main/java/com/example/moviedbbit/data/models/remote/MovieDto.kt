package com.example.moviedbbit.data.models.remote

import com.google.gson.annotations.SerializedName

data class MovieListResponse(
    val page: Int,
    val results: List<MovieDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)

data class MovieDto(
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int,
    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    val popularity: Double,
    val adult: Boolean,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String
)

data class MovieDetailsResponse(
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int,
    val genres: List<GenreDto>,
    val runtime: Int?,
    val status: String,
    val tagline: String?,
    val credits: CreditsResponse?,
    val videos: VideosResponse?,
    val similar: MovieListResponse?
)

data class GenreDto(
    val id: Int,
    val name: String
)

data class GenreListResponse(
    val genres: List<GenreDto>
)

data class CreditsResponse(
    val cast: List<CastMemberDto>,
    val crew: List<CrewMemberDto>
)

data class CastMemberDto(
    val id: Int,
    val name: String,
    val character: String,
    @SerializedName("profile_path")
    val profilePath: String?,
    val order: Int
)

data class CrewMemberDto(
    val id: Int,
    val name: String,
    val job: String,
    val department: String,
    @SerializedName("profile_path")
    val profilePath: String?
)

data class VideosResponse(
    val results: List<VideoDto>
)

data class VideoDto(
    val id: String,
    val key: String,
    val name: String,
    val site: String,
    val type: String,
    val official: Boolean
)