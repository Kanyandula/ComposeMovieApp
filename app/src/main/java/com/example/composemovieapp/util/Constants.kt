package com.example.composemovieapp.util

import com.example.composemovieapp.R
import java.text.SimpleDateFormat
import java.util.*

const val TAG = "AppDebug"

//PAGINATION
const val PAGE_SIZE = 20

//for detail fragment
const val IMAGE_HEIGHT = 700

const val MOVIE_DETAILS_URL = "movie/{movie_id}?api_key=633c29f375c222facd212d386926bcec"
const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w780"
const val DEFAULT_MOVIE_IMAGE = R.drawable.images
const val DEFAULT_BACK_IMAGE = R.drawable.ic_baseline_arrow_back_24
const val MOVIE_WEB_URL = "https://www.themoviedb.org/movie/"


val movieResponseDateFormat = SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH)
val movieDisplayDateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH)

// sate keys for various events
const val STATE_KEY_PAGE = "movie.state.page.key"
const val STATE_KEY_QUERY = "movie.state.query.key"
const val STATE_KEY_LIST_POSITION = "movie.state.query.list_position"
const val STATE_KEY_SELECTED_CATEGORY = "movie.state.query.selected_category"
const val STATE_KEY_MOVIE = "movie.state.movie.key"

