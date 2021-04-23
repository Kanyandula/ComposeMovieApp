package com.example.composemovieapp.domain.model

import android.os.Parcelable
import com.example.composemovieapp.di.NetworkModule
import com.example.composemovieapp.network.model.Genre
import com.example.composemovieapp.util.movieDisplayDateFormat
import com.example.composemovieapp.util.movieResponseDateFormat
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import java.text.ParseException

@Parcelize
data class Movie(

    val backdrop_path: String,
    val genres: List<Genre> = listOf(),
    val homepage: String,
    val id: Int,
    val media_type: String,
    val original_language: String,
    val original_title: String,
    var status: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val runtime: Int,
    val title: String,
    val vote_average: Double,
    val vote_count: Int
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
        return try {
            release_date.let { movieResponseDateFormat.parse(release_date)?.let { movieDisplayDateFormat.format(it) } ?: "N/A" }
        } catch (pe: ParseException) { "" }
    }
}


