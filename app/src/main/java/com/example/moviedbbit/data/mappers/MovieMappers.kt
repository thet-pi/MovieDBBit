package com.example.moviedbbit.data.mappers

import com.example.moviedbbit.data.models.local.FavoriteMovieEntity
import com.example.moviedbbit.data.models.remote.CastMemberDto
import com.example.moviedbbit.data.models.remote.GenreDto
import com.example.moviedbbit.data.models.remote.MovieDetailsResponse
import com.example.moviedbbit.data.models.remote.MovieDto
import com.example.moviedbbit.data.models.remote.VideoDto
import com.example.moviedbbit.domain.models.CastMember
import com.example.moviedbbit.domain.models.Genre
import com.example.moviedbbit.domain.models.Movie
import com.example.moviedbbit.domain.models.MovieDetails
import com.example.moviedbbit.domain.models.Video
import com.google.gson.Gson

// Image base URLs
private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"
private const val POSTER_SIZE = "w500"
private const val BACKDROP_SIZE = "w1280"
private const val PROFILE_SIZE = "w185"

fun MovieDto.toDomain(genres: List<Genre> = emptyList()): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterUrl = posterPath?.let { "$IMAGE_BASE_URL$POSTER_SIZE$it" },
        backdropUrl = backdropPath?.let { "$IMAGE_BASE_URL$BACKDROP_SIZE$it" },
        releaseDate = releaseDate,
        rating = voteAverage,
        voteCount = voteCount,
        genres = genres,
        popularity = popularity
    )
}

fun MovieDetailsResponse.toDomain(): MovieDetails {
    return MovieDetails(
        id = id,
        title = title,
        overview = overview,
        posterUrl = posterPath?.let { "$IMAGE_BASE_URL$POSTER_SIZE$it" },
        backdropUrl = backdropPath?.let { "$IMAGE_BASE_URL$BACKDROP_SIZE$it" },
        releaseDate = releaseDate,
        rating = voteAverage,
        voteCount = voteCount,
        genres = genres.map { it.toDomain() },
        runtime = runtime,
        status = status,
        tagline = tagline,
        cast = credits?.cast?.take(10)?.map { it.toDomain() } ?: emptyList(),
        videos = videos?.results?.filter { it.site == "YouTube" && it.type == "Trailer" }?.map { it.toDomain() } ?: emptyList(),
        similarMovies = similar?.results?.take(10)?.map { it.toDomain() } ?: emptyList()
    )
}

fun GenreDto.toDomain(): Genre {
    return Genre(
        id = id,
        name = name
    )
}

fun CastMemberDto.toDomain(): CastMember {
    return CastMember(
        id = id,
        name = name,
        character = character,
        profileUrl = profilePath?.let { "$IMAGE_BASE_URL$PROFILE_SIZE$it" }
    )
}

fun VideoDto.toDomain(): Video {
    return Video(
        id = id,
        key = key,
        name = name,
        site = site,
        type = type
    )
}

fun Movie.toFavoriteEntity(): FavoriteMovieEntity {
    return FavoriteMovieEntity(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterUrl?.substringAfter(POSTER_SIZE),
        backdropPath = backdropUrl?.substringAfter(BACKDROP_SIZE),
        releaseDate = releaseDate,
        voteAverage = rating,
        voteCount = voteCount,
        genres = Gson().toJson(genres.map { it.name })
    )
}

fun FavoriteMovieEntity.toDomain(): Movie {
    val genreNames: List<String> = try {
        Gson().fromJson(genres, Array<String>::class.java).toList()
    } catch (e: Exception) {
        emptyList()
    }
    
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterUrl = posterPath?.let { "$IMAGE_BASE_URL$POSTER_SIZE$it" },
        backdropUrl = backdropPath?.let { "$IMAGE_BASE_URL$BACKDROP_SIZE$it" },
        releaseDate = releaseDate,
        rating = voteAverage,
        voteCount = voteCount,
        genres = genreNames.mapIndexed { index, name -> Genre(index, name) },
        popularity = 0.0
    )
}