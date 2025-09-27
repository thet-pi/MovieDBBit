package com.example.moviedbbit.data.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "favorite_movies")
data class FavoriteMovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val genres: String, // JSON string of genres
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "movie_cache")
data class MovieCacheEntity(
    @PrimaryKey val id: Int,
    val category: String, // "popular", "top_rated", etc.
    val movieData: String, // JSON string of movie data
    val timestamp: Long,
    val page: Int
)

class Converters {
    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }
}