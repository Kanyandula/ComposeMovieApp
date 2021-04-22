package com.example.composemovieapp.presentation.ui.movie_list

sealed class MovieListEvent {

    object NewSearchEvent : MovieListEvent()
    object NextPageEvent : MovieListEvent()
    //restore after process death
    object RestoreStateEvent: MovieListEvent()
}