package com.example.composemovieapp.di

import com.example.composemovieapp.cache.MovieDao
import com.example.composemovieapp.cache.model.MovieEntityMapper
import com.example.composemovieapp.interactors.movie_detail.GetMovie
import com.example.composemovieapp.interactors.movie_list.RestoreMovies
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

    @ViewModelScoped
    @Provides
    fun provideRestoreMovies(
        movieDao: MovieDao,
        movieEntityMapper: MovieEntityMapper,

    ): RestoreMovies {
        return RestoreMovies(
            movieDao = movieDao,
            entityMapper = movieEntityMapper
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetMoviesDetails(
        retrofitService: RetrofitService,
        movieDao: MovieDao,
        movieEntityMapper: MovieEntityMapper,
        movieDtoMapper: MovieDtoMapper,
    ): GetMovie {
        return GetMovie(
            retrofitService = retrofitService,
            movieDao = movieDao,
           movieDtoMapper  = movieDtoMapper,
            entityMapper = movieEntityMapper
        )
    }

}