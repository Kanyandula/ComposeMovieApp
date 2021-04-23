package com.example.composemovieapp.util


import com.example.composemovieapp.network.model.Genre

fun List<Genre>.genreToCommaSeparatedString(): String {
    return this.joinToString(", ", transform = { it.name })
}




private fun convertGenreListToString(genres: List<Genre>): String {
    val genresString = StringBuilder()
    for(genre in genres){
        genresString.append("$genre,")
    }
    return genresString.toString()
}

private fun convertGenresToList(genresString: String?): ArrayList<String> {
    val list: ArrayList<String> = ArrayList()
    genresString?.let {
        for(genre  in it.split(",")){
            list.add(genre )
        }
    }
    return list
}



