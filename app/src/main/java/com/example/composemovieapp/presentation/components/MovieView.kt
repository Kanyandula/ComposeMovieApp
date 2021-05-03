package com.example.composemovieapp.presentation.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.composemovieapp.domain.model.Movie
import com.example.composemovieapp.presentation.compose.layout.MovieInfo
import com.example.composemovieapp.util.DEFAULT_MOVIE_IMAGE
import com.example.composemovieapp.util.IMAGE_HEIGHT
import com.example.composemovieapp.util.loadPicture
import kotlinx.coroutines.ExperimentalCoroutinesApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.ui.platform.LocalContext
import com.example.composemovieapp.util.MOVIE_WEB_URL


@ExperimentalCoroutinesApi
@Composable
fun MovieView(
    movie: Movie,
    movieId: Int?
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        item {

            movie.posterImage.let { url ->
                val image = loadPicture(url = url, defaultImage = DEFAULT_MOVIE_IMAGE).value

                    image?.let { img ->

                        Image(
                            bitmap = img.asImageBitmap(),
                            contentDescription = "Movie Featured Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IMAGE_HEIGHT.dp),
                            contentScale = ContentScale.Crop
                        )

                    }

                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                ) {

                    MovieInfo(movie = movie, movieId = movieId)
                   // MyButton(movieId = movieId)

                }



            }
        }
    }





@Composable
fun BackButton(
    onBackPressed: () -> Unit
) {

    IconButton(modifier = Modifier.
    then(Modifier.size(24.dp)),
        onClick =  onBackPressed) {
        Icon(
            Icons.Rounded.ArrowBack,
            "back button",
            tint = Color.White)
    }
}















