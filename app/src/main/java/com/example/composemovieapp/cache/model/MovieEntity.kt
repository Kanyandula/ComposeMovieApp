package com.example.composemovieapp.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class  MovieEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name ="backdrop_path")
    var backdropPath: String,

//     @ColumnInfo(name ="genres")
//    var genres:  String = "",

    @ColumnInfo(name ="homepage")
    var homepage: String,

    @ColumnInfo(name ="media_type")
    var mediaType: String,

    @ColumnInfo(name ="original_language")
    var originalLanguage: String,

    @ColumnInfo(name ="original_title")
    var originalTitle: String,

    @ColumnInfo(name ="overview")
    var overview: String,

    @ColumnInfo(name ="popularity")
    var popularity: Double,

    @ColumnInfo(name ="poster_path")
    var posterPath: String,

    @ColumnInfo(name ="release_date")
    var releaseDate: String,


    @ColumnInfo(name ="runtime")
    var runtime: Int,


    @ColumnInfo(name ="status")
    var status: String,


    @ColumnInfo(name ="title")
    var title: String,


    @ColumnInfo(name ="vote_average")
    var voteAverage: Double,

    @ColumnInfo(name ="vote_count")
    var voteCount: Int
)