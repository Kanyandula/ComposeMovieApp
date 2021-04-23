package com.example.composemovieapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.example.composemovieapp.presentation.navigation.Screen
import com.example.composemovieapp.presentation.ui.movie.MovieDetailScreen
import com.example.composemovieapp.presentation.ui.movie.MovieDetailViewModel
import com.example.composemovieapp.presentation.ui.movie_list.MovieListScreen
import com.example.composemovieapp.presentation.ui.movie_list.MovieListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Screen.MovieList.route) {

                composable(
                    route = Screen.MovieList.route)
                   { navBackStackEntry ->
                    val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
                    val viewModel: MovieListViewModel = viewModel("MovieListViewModel", factory)
                    MovieListScreen(
                        isDarkTheme =(application as BaseApplication).isDark.value,
                        onToggleTheme = (application as BaseApplication)
                        ::toggleLightTheme,
                        onNavigateToMovieDetailScreen = navController::navigate,
                        viewModel =viewModel )
                }

                composable(
                    route = Screen.MovieDetail.route + "/{movieId}",
                    arguments = listOf(navArgument("movieId"){
                        type = NavType.IntType
                    })
                ){ navBackStackEntry ->
                    val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
                    val detailViewModel: MovieDetailViewModel = viewModel("MovieDetailViewModel", factory)
                    MovieDetailScreen(
                        isDarkTheme = (application as BaseApplication).isDark.value,
                        movieId = navBackStackEntry.arguments?.getInt("movieId") ,
                        viewModel = detailViewModel )

                }
            }
            
        }

    }
}