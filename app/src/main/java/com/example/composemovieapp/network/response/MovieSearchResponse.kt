package com.example.composemovieapp.network.response


import com.example.composemovieapp.network.model.MovieDto
import com.google.gson.annotations.SerializedName

data class MovieSearchResponse (
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val movies: List<MovieDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Double
        )