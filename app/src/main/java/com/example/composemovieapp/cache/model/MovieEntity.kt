package com.example.composemovieapp.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.composemovieapp.network.model.Genre
import com.google.gson.Gson




@Entity(tableName = "movies")
data class  MovieEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int? = null,

    @ColumnInfo(name ="backdrop_path")
    var backdropPath: String? = null,

    @ColumnInfo(name ="homepage")
    var homepage: String? = null,

    @ColumnInfo(name ="media_type")
    var mediaType: String? = null,

    @ColumnInfo(name ="original_language")
    var originalLanguage: String? = null,

    @ColumnInfo(name ="original_title")
    var originalTitle: String? = null,

    @ColumnInfo(name ="overview")
    var overview: String? = null,

    @ColumnInfo(name ="popularity")
    var popularity: Double? =null,

    @ColumnInfo(name ="poster_path")
    var posterPath: String? = null,

    @ColumnInfo(name ="release_date")
    var releaseDate: String? = null,


    @ColumnInfo(name ="runtime")
    var runtime: Int? = null,


    @ColumnInfo(name ="status")
    var status: String? = null,


    @ColumnInfo(name ="title")
    var title: String? = null,


    @ColumnInfo(name ="vote_average")
    var voteAverage: Double? = null,

    @ColumnInfo(name ="vote_count")
    var voteCount: Int? = null,

    @ColumnInfo(name ="genres")
    var genres:  List<Genre> = listOf() ,
)

class Converters {

    @TypeConverter
    fun listToJson(value: List<Genre>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Genre>::class.java).toList()
}

