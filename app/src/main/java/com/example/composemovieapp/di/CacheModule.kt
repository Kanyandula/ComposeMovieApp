package com.example.composemovieapp.di

import androidx.room.Room
import com.example.composemovieapp.cache.MovieDao
import com.example.composemovieapp.cache.database.MovieDatabase
import com.example.composemovieapp.cache.model.MovieEntityMapper
import com.example.composemovieapp.presentation.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {


    /**
     * Not recommended to be used in production coz it destroy cache data and session data
     * and rebuild it
     */

    @Singleton
    @Provides
    fun provideDb(app: BaseApplication): MovieDatabase{
        return Room.databaseBuilder(app, MovieDatabase::class.java, MovieDatabase
            .DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(app: MovieDatabase): MovieDao{
        return  app.movieDao()
    }

    @Singleton
    @Provides
    fun provideCacheMovieMapper():MovieEntityMapper{
        return MovieEntityMapper()
    }


}