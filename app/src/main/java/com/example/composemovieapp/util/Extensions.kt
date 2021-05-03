package com.example.composemovieapp.util


import com.example.composemovieapp.network.model.Genre
import kotlinx.coroutines.flow.MutableSharedFlow

fun List<Genre>.genreToCommaSeparatedString(): String {
    return this.joinToString(", ", transform = { it.name })
}




private fun convertGenreListToString(genres: List<Genre>): String {
    val genresString = StringBuilder()
    for(genre in genres){
        genresString.append("${genre},")
    }
    return genresString.toString()
}

private fun convertGenresToList(genresString: Genre?):  List<String> {
    val list: ArrayList<String> = ArrayList()
    genresString?.let {
        for(genre  in it.toString()){
            list.add(genre.toString())
        }
    }
    return list
}

fun <T> mutableEventFlow(): MutableSharedFlow<T> {
    return MutableSharedFlow(
        replay = 0,
        extraBufferCapacity = 1
    )
}


