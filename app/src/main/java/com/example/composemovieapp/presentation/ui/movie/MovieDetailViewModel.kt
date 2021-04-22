package com.example.composemovieapp.presentation.ui.movie

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composemovieapp.domain.model.Movie
import com.example.composemovieapp.repository.MovieRepository
import com.example.composemovieapp.util.STATE_KEY_MOVIE
import com.example.composemovieapp.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@HiltViewModel
class MovieDetailViewModel
@Inject
constructor(
    private  val movieRepository: MovieRepository,
    private @Named("api_key") val key: String,
    private val state: SavedStateHandle,
): ViewModel(){
    val movie: MutableState<Movie?> = mutableStateOf(null)

    val loading = mutableStateOf(false)
    val onLoad:MutableState<Boolean> = mutableStateOf(false)

    init {
        // restore if process dies
        state.get<Int>(STATE_KEY_MOVIE)?.let{ movieId ->
            onTriggerEvent(MovieEvent.GetMovieEvent(movieId))
        }
    }

    fun onTriggerEvent(event: MovieEvent){
        viewModelScope.launch {
            try {
                when(event){
                    is MovieEvent.GetMovieEvent -> {
                        if(movie.value == null){
                            getMovieDetails(event.id)

                        }
                    }
                }
            }catch (e: Exception){
                Log.e(TAG, "launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            }
        }
    }

    private suspend fun getMovieDetails(id: Int){
        loading.value = true
        // simulate a delay to show loading
        delay(1000)
        val movie = movieRepository.getDetails( id=id)
        this.movie.value = movie
        state.set(STATE_KEY_MOVIE, movie.id)
        loading.value = false
    }



}