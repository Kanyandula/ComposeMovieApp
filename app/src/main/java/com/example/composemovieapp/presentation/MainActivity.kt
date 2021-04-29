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
import com.example.composemovieapp.datastore.SettingsDataStore
import com.example.composemovieapp.presentation.navigation.Screen
import com.example.composemovieapp.presentation.ui.movie.MovieDetailScreen
import com.example.composemovieapp.presentation.ui.movie.MovieDetailViewModel
import com.example.composemovieapp.presentation.ui.movie_list.MovieListScreen
import com.example.composemovieapp.presentation.ui.movie_list.MovieListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var connectivityManager:com.example.composemovieapp.presentation.util.ConnectivityManager
    @Inject
    lateinit var settingsDataStore: SettingsDataStore




    override fun onStart() {
        super.onStart()
        connectivityManager.registerConnectionObserver(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManager.unregisterConnectionObserver(this)
    }

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
                        isDarkTheme =settingsDataStore.isDark.value,
                        isNetworkAvailable = connectivityManager.isNetworkAvailable.value,
                        onToggleTheme = settingsDataStore
                        ::toggleTheme,
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
                        isDarkTheme = settingsDataStore.isDark.value,
                        isNetworkAvailable = connectivityManager.isNetworkAvailable.value,
                        movieId = navBackStackEntry.arguments?.getInt("movieId") ,
                        viewModel = detailViewModel )

                }
            }
            
        }

    }
}