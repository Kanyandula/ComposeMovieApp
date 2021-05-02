package com.example.composemovieapp.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.composemovieapp.cache.MovieDao
import com.example.composemovieapp.cache.model.Converters
import com.example.composemovieapp.cache.model.MovieEntity


@Database(entities = [MovieEntity::class], version = 3)
@TypeConverters( Converters::class)
abstract  class  MovieDatabase: RoomDatabase(){

    abstract fun movieDao(): MovieDao

    companion object{
     const val DATABASE_NAME ="movie_db"
    }
}