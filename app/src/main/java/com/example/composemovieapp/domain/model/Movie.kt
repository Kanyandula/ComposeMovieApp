package com.example.composemovieapp.domain.model

import android.os.Parcelable

import com.example.composemovieapp.di.NetworkModule
import com.example.composemovieapp.network.model.Genre

import com.example.composemovieapp.util.movieDisplayDateFormat
import com.example.composemovieapp.util.movieResponseDateFormat
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import java.text.ParseException

@Parcelize
data class Movie(
    val adult: Boolean? = null,
    val backdrop_path: String? = null,
    val genre_ids: List<Int>? = null,
    @SerializedName("genres")
    val genres: List<Genre> = listOf() ,
val homepage: String? = null,
    val status: String? = null,
    val release_date: String? = null,
    val media_type: String? = null,
    val id: Int? = null,
    @SerializedName("media_type")
    val mediaType: String? = null,
    val original_language: String? = null,
    val original_title: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val poster_path: String? = null,
    @SerializedName("release_date", alternate = ["first_air_date"])
    val releaseDate: String? = null,
    val runtime: Int? = null,
    val title: String? = null,
    val video: Boolean? = null,
    val vote_average: Double? = null,
    val vote_count: Int? = null
) : Parcelable{


    /**
     * http link to the image in The API
     * val imageUrl get() = "https://image.tmdb.org/t/p/w780/$backdrop_path?"
     */
    @IgnoredOnParcel
    val imageUrl : String
        get() = String.format(NetworkModule.POSTER_PATH, backdrop_path)

    @IgnoredOnParcel
    val posterImage : String
        get() = String.format(NetworkModule.POSTER_PATH, poster_path)

    @IgnoredOnParcel
    val rating : String
        get() = String.format("%s Rating ",vote_average)

    @IgnoredOnParcel
    val runTime : String
        get() = String.format("%s min",runtime)




    @IgnoredOnParcel
    val date: String get() {
        return try { releaseDate?.let { movieResponseDateFormat.parse(releaseDate)?.let { movieDisplayDateFormat.format(it) } ?: "N/A" } ?: "" } catch (pe: ParseException) { "" }
    }
}


