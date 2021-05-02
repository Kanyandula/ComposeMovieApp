package com.example.composemovieapp.network

import com.example.composemovieapp.network.model.MovieDto
import com.example.composemovieapp.network.response.CreditResponse
import com.example.composemovieapp.network.response.MovieSearchResponse
import com.example.composemovieapp.util.MOVIE_DETAILS_URL
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RetrofitService {

    @GET("search/multi")
    suspend fun search(
        @Query("api_key") key: String,
        @Query("query")   query: String,
        @Query("page") page: Int
    ): MovieSearchResponse


     @GET("movie/latest")
    suspend fun get(
        @Query("api_key") key: String,


    ): MovieDto


    @GET(MOVIE_DETAILS_URL)
    suspend fun getDetails(
        @Path("movie_id") id: Int,

    ): MovieDto

    @GET("{media_type}/{id}/credits")
    suspend fun credits(
        @Path("media_type") mediaType: String,
        @Path("id") id: Int
    ): Response<CreditResponse>

}