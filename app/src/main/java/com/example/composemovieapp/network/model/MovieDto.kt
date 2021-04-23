package com.example.composemovieapp.network.model

import com.google.gson.annotations.SerializedName
import java.util.Collections.emptyList

class MovieDto(

    @SerializedName("backdrop_path")
    val backdropPath: String,

    @SerializedName("genres")
    val genres: List<Genre> = emptyList(),

    @SerializedName("homepage")
    val homepage: String,

    @SerializedName("id")
    val id: Int,

    @SerializedName("media_type")
    val mediaType: String,

    @SerializedName("original_language")
    val originalLanguage: String,

    @SerializedName("original_title")
    val originalTitle: String,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("popularity")
    val popularity: Double,

    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("release_date")
    val releaseDate: String,

    @SerializedName("runtime")
    val runtime: Int,


    @SerializedName("status")
    var status: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("vote_count")
    val voteCount: Int
        )