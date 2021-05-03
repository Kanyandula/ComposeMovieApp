package com.example.composemovieapp.presentation.ui.movie

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composemovieapp.domain.model.Movie
import com.example.composemovieapp.interactors.movie_detail.GetMovie
import com.example.composemovieapp.presentation.ui.util.DialogQueue
import com.example.composemovieapp.presentation.util.ConnectivityManager
import com.example.composemovieapp.util.STATE_KEY_MOVIE
import com.example.composemovieapp.util.TAG
import com.example.composemovieapp.util.mutableEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MovieDetailViewModel
@Inject
constructor(
    private val getMovie: GetMovie,
    private val connectivityManager: ConnectivityManager,
    private val state: SavedStateHandle,
): ViewModel(){
    val movie: MutableState<Movie?> = mutableStateOf(null)

    val loading = mutableStateOf(false)
    val onLoad:MutableState<Boolean> = mutableStateOf(false)
    val dialogQueue = DialogQueue()


    private val _shouldNavigateUp = mutableEventFlow<Boolean>()
    val shouldNavigateUp = _shouldNavigateUp


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

    private  fun getMovieDetails(id: Int){
        getMovie.execute(id,connectivityManager.isNetworkAvailable.value).onEach { dataState ->
            loading.value = dataState.loading
            dataState.data?.let { data ->
                movie.value = data
                state.set(STATE_KEY_MOVIE, data.id)
            }
            dataState.error?.let { error ->
                Log.e(TAG,"getMovie: $error")
                dialogQueue.appendErrorMessage("Error", error)
            }

        }.launchIn(viewModelScope)
    }


    fun onBackPressed() {
        _shouldNavigateUp.tryEmit(true)
    }

}