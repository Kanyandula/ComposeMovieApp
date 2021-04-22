package com.example.composemovieapp.repository

import com.example.composemovieapp.domain.model.Movie
import com.example.composemovieapp.network.RetrofitService
import com.example.composemovieapp.network.model.MovieDtoMapper

class MovieRepositoryImpl(
private  val retrofitService: RetrofitService,
private val mapper: MovieDtoMapper
): MovieRepository {

    override suspend fun search(key: String,query: String,page: Int): List<Movie> {
        return mapper.toDomainList(retrofitService.search(key = key, query= query, page = page).movies)
    }

    override suspend fun get(key: String  ): Movie {
        return mapper.mapToDomainModel(retrofitService.get(key = key  ))
    }

    override suspend fun getDetails(id:Int  ): Movie {
        return mapper.mapToDomainModel(retrofitService.getDetails( id = id ))
    }
}