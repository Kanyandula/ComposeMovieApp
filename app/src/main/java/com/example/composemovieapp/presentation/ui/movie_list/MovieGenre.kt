package com.example.composemovieapp.presentation.ui.movie_list

import com.example.composemovieapp.presentation.ui.movie_list.MovieGenre.*

enum class MovieGenre (val value: String){

   ACTION ("Action"),
   ADVENTURE ( "Adventure"),
    ANIMATION ("Animation"),
    COMEDY ("Comedy"),
    DOCUMENTARY ("Documentary"),
    DRAMA ("Drama"),
    FAMILY ("Family"),
    HORROR ("Horror"),
    ROMANCE ("Romance"),
    THRILLER("Thriller"),
    WAR ("War"),
    WESTERN ("Western")
}

fun getAllMovieGenres(): List<MovieGenre>{
 return listOf(ACTION,ADVENTURE,ANIMATION,COMEDY,DOCUMENTARY,DRAMA, FAMILY,HORROR,ROMANCE,THRILLER,WAR,WESTERN)
}

fun getMovieGenre(value: String): MovieGenre? {
 val map = MovieGenre.values().associateBy(MovieGenre::value)
 return map[value]
}