package com.example.composemovieapp.di

import com.example.composemovieapp.cache.MovieDao
import com.example.composemovieapp.cache.model.MovieEntityMapper
import com.example.composemovieapp.interactors.movie_list.SearchMovies
import com.example.composemovieapp.network.RetrofitService
import com.example.composemovieapp.network.model.MovieDtoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object InteractorsModule {

    @ViewModelScoped
    @Provides
    fun provideSearchMovies(
        retrofitService: RetrofitService,
        movieDao: MovieDao,
        movieEntityMapper: MovieEntityMapper,
        movieDtoMapper: MovieDtoMapper,
    ): SearchMovies{
        return SearchMovies(
            retrofitService = retrofitService,
            movieDao = movieDao,
            dtoMapper = movieDtoMapper,
            entityMapper = movieEntityMapper
        )
    }
}