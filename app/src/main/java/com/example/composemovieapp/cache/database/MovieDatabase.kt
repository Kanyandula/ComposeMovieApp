package com.example.composemovieapp.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.composemovieapp.cache.MovieDao
import com.example.composemovieapp.cache.model.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract  class  MovieDatabase: RoomDatabase(){

    abstract fun movieDao(): MovieDao

    companion object{
     val DATABASE_NAME ="movie_db"
    }
}