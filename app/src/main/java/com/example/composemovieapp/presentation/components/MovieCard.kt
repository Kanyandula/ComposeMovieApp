package com.example.composemovieapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.composemovieapp.domain.model.Movie
import com.example.composemovieapp.presentation.compose.layout.ColumnSpacer
import com.example.composemovieapp.util.DEFAULT_MOVIE_IMAGE
import com.example.composemovieapp.util.loadPicture

@Composable
fun MovieCard(movie: Movie, isFirstCard: Boolean = false, onClick: () -> Unit) {
    RowSpacer(value = if(isFirstCard) 16 else 4)
    Column(modifier = Modifier
        .width(200.dp)
        .padding(vertical = 10.dp)
    ) {
        Card(
            elevation = 5.dp,
            modifier = Modifier.clickable { onClick() },
            shape = MaterialTheme.shapes.medium,
            ) {
            val image = loadPicture(url =  movie.imageUrl, defaultImage = DEFAULT_MOVIE_IMAGE).value
            if (image != null) {
                Image(
                    bitmap = image.asImageBitmap(),
                    contentDescription = "Movie Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(225.dp),
                    contentScale = ContentScale.Crop,
                )
            }
        }

        ColumnSpacer(8)
        val padding = Modifier.padding(horizontal = 8.dp)
        Text(
            text = movie.title,
            style = MaterialTheme.typography.body2,
            maxLines = 2,
            fontWeight = FontWeight.Bold,
            modifier = padding
        )
        ColumnSpacer(4)

            Text(
                text = movie.date,
                style = MaterialTheme.typography.overline,
                modifier = padding
            )

        Spacer(Modifier.height(8.dp))
    }
    RowSpacer(value = 4)
}

@Composable
fun RowSpacer(value: Int) = Spacer(modifier = Modifier.width(value.dp))


