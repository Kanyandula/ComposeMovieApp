package com.example.composemovieapp.presentation.ui.movie

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composemovieapp.presentation.components.LoadingMovieShimmer
import com.example.composemovieapp.presentation.components.MovieView
import com.example.composemovieapp.presentation.theme.AppTheme
import com.example.composemovieapp.util.IMAGE_HEIGHT
import com.example.composemovieapp.util.TAG
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@Composable
fun MovieDetailScreen(
    isDarkTheme: Boolean,
    movieId: Int?,
    viewModel: MovieViewModel
){
    Log.d(TAG, "MovieDetailScreen: $viewModel")
    Text("MovieDetailScreen: $movieId")


//    val loading = viewModel.loading.value
//    val movie = viewModel.movie.value
//    val scaffoldState = rememberScaffoldState()
//
//    AppTheme(
//        darkTheme = application.isDark.value,
//        displayProgressBar = loading,
//        scaffoldState = scaffoldState
//
//    ) {
//        Scaffold(
//            scaffoldState = scaffoldState,
//            snackbarHost = {
//                scaffoldState.snackbarHostState
//            }
//        ) {
//            Box(modifier = Modifier.fillMaxSize()) {
//                if (loading && movie == null)
//                    LoadingMovieShimmer(imageHeight = IMAGE_HEIGHT.dp)
//                else {
//                    movie?.let {
//                        if (it.id == 1) { // create a fake error
//                            snackbarController.getScope().launch {
//                                snackbarController.showSnackbar(
//                                    scaffoldState = scaffoldState,
//                                    message = "An error occurred with this ",
//                                    actionLabel = "Ok"
//                                )
//                            }
//                        } else {
//                            MovieView(
//                                movie = it,
//
//                                )
//                        }
//                    }
//                }
//
//            }
//        }
//
//    }
}