package com.example.composemovieapp.util


import com.example.composemovieapp.network.model.Genre

fun List<Genre>.genreToCommaSeparatedString(): String {
    return this.joinToString(", ", transform = { it.name.toString() })
}

