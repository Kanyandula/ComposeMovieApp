package com.example.composemovieapp.presentation.ui.movie


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composemovieapp.presentation.components.LoadingMovieShimmer
import com.example.composemovieapp.presentation.components.MovieView
import com.example.composemovieapp.presentation.theme.AppTheme
import com.example.composemovieapp.util.IMAGE_HEIGHT

import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalCoroutinesApi
@Composable
fun MovieDetailScreen(
    isDarkTheme: Boolean,
    isNetworkAvailable: Boolean,
    movieId: Int?,
    viewModel: MovieDetailViewModel
) {

    if (movieId == null) {
        TODO("Show Invalid Movie")
    } else {
        val onLoad = viewModel.onLoad.value
        if (!onLoad) {
            viewModel.onLoad.value = true
            viewModel.onTriggerEvent(MovieEvent.GetMovieEvent(movieId))

        }
        val loading = viewModel.loading.value
        val movie = viewModel.movie.value
        val dialogQueue = viewModel.dialogQueue
        val scaffoldState = rememberScaffoldState()

        AppTheme(
            darkTheme = isDarkTheme,
            isNetworkAvailable = isNetworkAvailable,
            displayProgressBar = loading,
            scaffoldState = scaffoldState,
            dialogQueue = dialogQueue.queue.value

        ) {
            Scaffold(
                scaffoldState = scaffoldState,
                snackbarHost = {
                    scaffoldState.snackbarHostState
                }
            ) {
                Box(modifier = Modifier.fillMaxWidth()
                    .padding(8.dp) ) {
                    if (loading && movie == null)
                        LoadingMovieShimmer(imageHeight = IMAGE_HEIGHT.dp)
                    else {
                        movie?.let {
                            if (it.id == 1) { // create a fake error

                            } else {
                                MovieView(
                                    movie = it,

                                    )
                            }
                        }
                    }

                }
            }

        }
    }

}