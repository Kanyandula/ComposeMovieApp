package com.example.composemovieapp.interactors.movie_list

import android.app.DownloadManager
import com.example.composemovieapp.cache.MovieDao
import com.example.composemovieapp.cache.model.MovieEntityMapper
import com.example.composemovieapp.domain.data.DataState
import com.example.composemovieapp.domain.model.Movie
import com.example.composemovieapp.domain.util.DomainMapper
import com.example.composemovieapp.network.RetrofitService
import com.example.composemovieapp.network.model.MovieDtoMapper
import com.example.composemovieapp.util.PAGE_SIZE
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class SearchMovies(
    private val movieDao: MovieDao,
    private val retrofitService: RetrofitService,
    private val entityMapper: MovieEntityMapper,
    private val dtoMapper: MovieDtoMapper,

    ){
    fun  execute(
       key: String,
       page: Int,
       query: String,
       isNetworkAvailable:Boolean,
    ) : Flow<DataState<List<Movie>>> = flow {
        try {
            emit(DataState.loading<List<Movie>>())

            // for pagination sake
            delay(1000)

            //force error for testing
            if (query == "error"){
                throw  Exception("Search FAILED")
            }

            if (isNetworkAvailable) {

                // Convert: NetworkMovieEntity -> Movie -> MovieCacheEntity
                val movies = getMoviesFromNetwork(key = key, page = page, query = query)

                //insert into the cache
                movieDao.insertMovies(entityMapper.toEntityList(movies))
            }

            // query the cache
            val  cacheResult = if (query.isBlank()){
                movieDao.getAllMovies(
                    pageSize = PAGE_SIZE,
                    page = page
                )
            }
            else{
                movieDao.searchMovies(
                    query = query,
                    pageSize = PAGE_SIZE,
                    page = page
                )
            }

            //emit List<Movie> from the cache
            val  list = entityMapper.fromEntityList(cacheResult)
            emit(DataState.success(list))


        }catch (e: Exception){
            emit(DataState.error<List<Movie>>(e.message?: "Unknown error"))
        }
    }


    // An exception will be thrown in case there is no Network connection
    private suspend fun  getMoviesFromNetwork(
        key: String,
        page: Int,
        query: String
    ): List<Movie>{
        return dtoMapper.toDomainList(
            retrofitService.search(
                key = key,
                page = page,
                query = query).movies
        )

    }
}