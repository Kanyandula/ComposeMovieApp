package com.example.composemovieapp.presentation.ui.movie_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composemovieapp.presentation.ui.movie_list.MovieListEvent.*
import com.example.composemovieapp.domain.model.Movie
import com.example.composemovieapp.interactors.movie_list.RestoreMovies
import com.example.composemovieapp.interactors.movie_list.SearchMovies
import com.example.composemovieapp.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
@ExperimentalCoroutinesApi
class MovieListViewModel
@Inject
constructor(
    private val searchMovies: SearchMovies,
    private val restoreMovies: RestoreMovies,
    private @Named("api_key") val key: String,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val movies: MutableState<List<Movie>> = mutableStateOf(ArrayList())

    val query = mutableStateOf("Movie")



    val selectedCategory: MutableState<MovieGenre?> = mutableStateOf(null)

    var categoryScrollPosition: Float = 0f
    val loading = mutableStateOf(false)
    val page = mutableStateOf(1)
    private var movieListScrollPosition = 0


    init {

        savedStateHandle.get<Int>(STATE_KEY_PAGE)?.let { p ->
            Log.d(TAG, "restoring page: ${p}")
            setPage(p)
        }
        savedStateHandle.get<String>(STATE_KEY_QUERY)?.let { q ->
            setQuery(q)
        }
        savedStateHandle.get<Int>(STATE_KEY_LIST_POSITION)?.let { p ->
            Log.d(TAG, "restoring scroll position: ${p}")
            setListScrollPosition(p)
        }
        savedStateHandle.get<MovieGenre>(STATE_KEY_SELECTED_CATEGORY)?.let { c ->
            setSelectedCategory(c)
        }
        // Were they doing something before the process died?
        if(movieListScrollPosition != 0){
            onTriggerEvent(RestoreStateEvent)
        }
        else {

            onTriggerEvent(NewSearchEvent)
        }

    }

    fun onTriggerEvent(event: MovieListEvent){
        viewModelScope.launch {
            try {
                when(event){
                    is NewSearchEvent -> {
                        newSearch()
                    }
                    is NextPageEvent -> {
                        nextPage()
                    }
                    is RestoreStateEvent -> {
                        restoreState()
                    }
                }
            }catch (e: Exception){
                Log.e(TAG, "launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            }
            finally {
                Log.d(TAG, "launchJob: finally called.")
            }
        }
    }

    private  fun restoreState(){
        restoreMovies.execute( page = page.value, query =
        query.value).onEach { dataState ->
            loading.value = dataState.loading
            dataState.data?.let { list ->
                movies.value = list
            }
            dataState.error?.let { error ->
                Log.e(TAG, "restoreState: ${error}")
                // TODO("Handle error")


            }

        }.launchIn(viewModelScope)

    }

    //use case #1
    private  fun newSearch() {
        Log.d(TAG,"newSearch : query: ${query.value}, page: ${page.value}")
        resetSearchState()
        searchMovies.execute(key = key, page = page.value, query =
        query.value).onEach { dataState ->
            loading.value = dataState.loading
            dataState.data?.let { list ->
                movies.value = list
            }
            dataState.error?.let { error ->
                Log.e(TAG, "newSearch: ${error}")
               // TODO("Handle error")


            }

        }.launchIn(viewModelScope)


    }

    //use case #2
    private  fun nextPage() {
        //prevent duplicate event due to recompose happening too quickly
        if((movieListScrollPosition + 1) >= (page.value * PAGE_SIZE)
        ){
            loading.value = true
            incrementPage()
            Log.d(TAG, "nextPage: triggered: ${page.value}")

            if(page.value > 1){
                searchMovies.execute(key = key, page = page.value, query =
                query.value).onEach { dataState ->
                    loading.value = dataState.loading
                    dataState.data?.let { list ->
                        appendMovies(list)
                    }
                    dataState.error?.let { error ->
                        Log.e(TAG, "nextPage: ${error}")
                       // TODO("Handle error")


                    }

                }.launchIn(viewModelScope)


            }
        }
    }
    /**
     * Append new movies to the current list of movies
     */
    private fun appendMovies(movies: List<Movie>) {
        val current = ArrayList(this.movies.value)
        current.addAll(movies)
        this.movies.value = current
    }

    private fun incrementPage() {
        //page.value = page.value + 1
        setPage(page.value + 1)
    }

    fun onChangeMovieScrollPosition(position: Int) {
        //movieListScrollPosition = position
        setListScrollPosition(position = position)
    }

    /**
     * Called when a new search is executed.
     */
    private fun resetSearchState() {
        movies.value = listOf()
        page.value = 1
        onChangeMovieScrollPosition(0)
        if (selectedCategory.value?.value != query.value)
            clearSelectedCategory()
    }

    private fun clearSelectedCategory() {
        setSelectedCategory(null)
        selectedCategory.value = null
    }

    fun onQueryChanged(query: String) {
        setQuery(query)

    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getMovieGenre(category)
        setSelectedCategory(newCategory)

        onQueryChanged(category)
    }

    fun onChangeCategoryScrollPosition(position: Float) {
        categoryScrollPosition = position
    }

    private fun setListScrollPosition(position: Int){
        movieListScrollPosition = position
        savedStateHandle.set(STATE_KEY_LIST_POSITION, position)
    }

    private fun setPage(page: Int){
        this.page.value = page
        savedStateHandle.set(STATE_KEY_PAGE, page)
    }

    private fun setSelectedCategory(category: MovieGenre?){
        selectedCategory.value = category
        savedStateHandle.set(STATE_KEY_SELECTED_CATEGORY, category)
    }

    private fun setQuery(query: String){
        this.query.value = query
        savedStateHandle.set(STATE_KEY_QUERY, query)
    }


}
