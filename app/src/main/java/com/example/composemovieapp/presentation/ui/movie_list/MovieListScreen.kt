package com.example.composemovieapp.presentation.ui.movie_list

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import com.example.composemovieapp.presentation.components.MovieList
import com.example.composemovieapp.presentation.components.SearchAppBar
import com.example.composemovieapp.presentation.theme.AppTheme
import com.example.composemovieapp.util.TAG
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun MovieListScreen (
    isDarkTheme: Boolean,
    onToggleTheme : () -> Unit ,
    onNavigateToMovieDetailScreen: (String) -> Unit,
    viewModel: MovieListViewModel

){

    Log.d(TAG, "MovieListScreen: $viewModel")
    val movies = viewModel.movies.value
    val query = viewModel.query.value
    val selectedCategory = viewModel.selectedCategory.value
    val loading = viewModel.loading.value
    val categoryScrollPosition = viewModel.categoryScrollPosition
    val page = viewModel.page.value
    val scaffoldState = rememberScaffoldState()
    AppTheme(
        darkTheme = isDarkTheme,
        displayProgressBar = loading,
        scaffoldState = scaffoldState
    ) {

        Scaffold(
            topBar = {
                SearchAppBar(
                    query = query,
                    onQueryChanged = viewModel::onQueryChanged,
                    onExecuteSearch = { viewModel.onTriggerEvent(MovieListEvent.NewSearchEvent) },
                    categories = getAllMovieGenres(),
                    selectedCategory = selectedCategory,
                    onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                    onToggleTheme =  onToggleTheme

                )
            },
            scaffoldState = scaffoldState,
            snackbarHost = {
                scaffoldState.snackbarHostState
            },

            ) {


            MovieList(
                loading = loading,
                movies = movies,
                onChangeMovieScrollPosition= viewModel::onChangeMovieScrollPosition,
                page = page,
                onNextPage = {viewModel.onTriggerEvent(MovieListEvent.NextPageEvent)},
               onNavigationToMovieDetailScreen =  onNavigateToMovieDetailScreen

            )


        }


    }

    }
