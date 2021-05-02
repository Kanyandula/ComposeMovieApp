package com.example.composemovieapp.presentation.components

import LoadingMovieListShimmer
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composemovieapp.domain.model.Movie
import com.example.composemovieapp.presentation.navigation.Screen
import com.example.composemovieapp.util.PAGE_SIZE
import kotlinx.coroutines.ExperimentalCoroutinesApi

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
    onNavigationToMovieDetailScreen: (String) -> Unit,

    ) {
    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colors.surface)

    ) {
        if (loading && movies.isEmpty()) {
                LoadingMovieListShimmer(imageHeight = 250.dp,)

        } else if (movies.isEmpty()) {
            NothingHere()
        } else {


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
                    Box(
                        modifier = Modifier.fillMaxWidth(0.5f)
                            .padding(8.dp)
                    ) {
                        MovieCard(
                            movie = movie,
                            onClick = {
                                val route = Screen.MovieDetail.route + "/${movie.id}"
                                onNavigationToMovieDetailScreen(route)
                            }
                        )
                    }

                }
            }
        }
    }
}













