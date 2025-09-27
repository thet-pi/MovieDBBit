package com.example.moviedbbit.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moviedbbit.data.models.local.Converters
import com.example.moviedbbit.data.models.local.FavoriteMovieEntity
import com.example.moviedbbit.data.models.local.MovieCacheEntity
import android.content.Context

@Database(
    entities = [FavoriteMovieEntity::class, MovieCacheEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {
    
    abstract fun favoriteMovieDao(): FavoriteMovieDao
    abstract fun movieCacheDao(): MovieCacheDao
    
    companion object {
        const val DATABASE_NAME = "movie_database"
    }
}