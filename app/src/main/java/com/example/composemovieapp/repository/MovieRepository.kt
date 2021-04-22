package com.example.composemovieapp.repository

import android.app.DownloadManager
import com.example.composemovieapp.domain.model.Movie
import com.example.composemovieapp.network.model.Language

interface MovieRepository {
    suspend fun search(key: String,query: String, page: Int ): List<Movie>

    suspend fun get(key: String  ): Movie

    suspend fun getDetails(id: Int ): Movie
}