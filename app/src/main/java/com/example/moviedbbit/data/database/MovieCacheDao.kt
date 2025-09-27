package com.example.moviedbbit.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviedbbit.data.models.local.MovieCacheEntity

@Dao
interface MovieCacheDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieCache(cache: MovieCacheEntity)
    
    @Query("SELECT * FROM movie_cache WHERE category = :category AND page = :page")
    suspend fun getCachedMovies(category: String, page: Int): List<MovieCacheEntity>
    
    @Query("DELETE FROM movie_cache WHERE category = :category")
    suspend fun clearCategoryCache(category: String)
    
    @Query("DELETE FROM movie_cache WHERE timestamp < :timestamp")
    suspend fun clearExpiredCache(timestamp: Long)
    
    @Query("SELECT * FROM movie_cache WHERE category = :category ORDER BY page")
    suspend fun getAllCachedMoviesForCategory(category: String): List<MovieCacheEntity>
}