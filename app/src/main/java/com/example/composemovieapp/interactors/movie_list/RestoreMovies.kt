package com.example.composemovieapp.interactors.movie_list

import android.util.Log
import com.example.composemovieapp.cache.MovieDao
import com.example.composemovieapp.cache.model.MovieEntityMapper
import com.example.composemovieapp.domain.data.DataState
import com.example.composemovieapp.domain.model.Movie
import com.example.composemovieapp.util.PAGE_SIZE
import com.example.composemovieapp.util.TAG
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception


class RestoreMovies(
    private val movieDao: MovieDao,
    private val entityMapper: MovieEntityMapper,
) {
    fun execute(
        page: Int,
        query: String
    ): Flow<DataState<List<Movie>>> = flow {
        try {
            emit(DataState.loading())
            delay(1000)

            val cacheResult = if (query.isBlank()) {
                movieDao.restoreAllMovies(
                    pageSize = PAGE_SIZE,
                    page = page
                )
            } else {
                movieDao.restoreMovies(
                    query = query,
                    pageSize = PAGE_SIZE,
                    page =page
                )

            }
            // convert the cache result into a list of movies

            val list = entityMapper.fromEntityList(cacheResult)
            emit(DataState.success(list))


        } catch (e: Exception) {
            Log.e(TAG, "execute: ${e.message}")
            emit(DataState.error<List<Movie>>(e.message ?: "Unknown error"))
        }
    }

}