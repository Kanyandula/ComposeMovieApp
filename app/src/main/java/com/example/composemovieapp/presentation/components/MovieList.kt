package com.example.composemovieapp.presentation.components

import android.os.Bundle
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.ui.tooling.preview.Preview
import com.example.composemovieapp.R
import com.example.composemovieapp.domain.model.Movie
import com.example.composemovieapp.presentation.components.util.SnackbarController
import com.example.composemovieapp.presentation.ui.movie_list.MovieListEvent
import com.example.composemovieapp.util.PAGE_SIZE
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalCoroutinesApi


@Composable
fun MovieList(
    loading: Boolean,
    movies: List<Movie>,
    onChangeMovieScrollPosition: (Int) -> Unit,
    page: Int,
    onNextPage: () -> Unit,
    onNavigationToMovieDetailScreen: (Int) -> Unit,

    ) {
    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colors.surface)

    ) {


            LazyVerticalGrid(
                cells = GridCells.Fixed(2)
            ) {
                itemsIndexed(
                    items = movies
                ) { index, movie ->
                    onChangeMovieScrollPosition(index)
                    if ((index + 1) >= (page * PAGE_SIZE) && !loading) {
                        onNextPage()

                    }
                  Box(modifier = Modifier.fillMaxWidth(0.5f)){
                      MovieCard(
                          movie = movie,
                          onClick = {
                              movie.id?.let { onNavigationToMovieDetailScreen(it) }
                          }
                      )
                  }

                }
            }
        }
    }












