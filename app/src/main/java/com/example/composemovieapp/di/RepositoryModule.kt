package com.example.composemovieapp.di


import com.example.composemovieapp.network.RetrofitService
import com.example.composemovieapp.network.model.MovieDtoMapper
import com.example.composemovieapp.repository.MovieRepository
import com.example.composemovieapp.repository.MovieRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRecipeRepository(
        retrofitService: RetrofitService,
        movieMapper: MovieDtoMapper,
    ): MovieRepository {
        return MovieRepositoryImpl(
            retrofitService =retrofitService,
            mapper = movieMapper
        )
    }
}

