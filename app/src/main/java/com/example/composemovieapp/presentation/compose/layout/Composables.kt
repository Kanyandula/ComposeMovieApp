package com.example.composemovieapp.presentation.compose.layout
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.composemovieapp.domain.model.Movie
import com.example.composemovieapp.util.MOVIE_WEB_URL
import java.util.*

@Composable
fun MovieInfo(movie: Movie, movieId: Int?  ) {
    ColumnSpacer(16)

        val padding = Modifier.padding(horizontal = 16.dp)
        movie.original_title?.let {
            Text(
                text = it,
                fontWeight = FontWeight.Bold,
                style = typography.h5,
                modifier = padding
            )
        }

    ColumnSpacer(8)
    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        movie.overview?.let {
            Text(
                text = it,
                style = typography.body2,
                maxLines = 15,
                modifier = padding
            )
        }
    }
    Spacer(Modifier.height(8.dp))
    MovieMetadata(movie = movie, modifier = Modifier.padding(horizontal = 16.dp))
    Spacer(Modifier.height(16.dp))

    Divider(
        modifier = Modifier
            .height((0.8).dp)
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.3F)
    )


    ColumnSpacer(8)
    MyButton(movieId = movieId)
    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        movie.genres.let {
            if (it != null) {
                Text(
                    text = it.joinToString(),
                    style = typography.body2,
                    maxLines = 4,
                    modifier = padding
                )
            }
        }
    }

}

@Composable
fun MyButton(movieId: Int?  ) {

    val context = LocalContext.current
    val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("$MOVIE_WEB_URL${movieId}"))

    OutlinedButton(onClick = { context.startActivity(webIntent) }, modifier = Modifier.padding(8.dp)) {
        Text( text = "OPEN IMDB",
            style = typography.h5)
    }
}



@Composable
fun MovieMetadata(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    val divider = "  •  "
    val text = buildAnnotatedString {
        val tagStyle = typography.overline.toSpanStyle().copy(
            background = MaterialTheme.colors.primary.copy(alpha = 0.8f)
        )
        withStyle(tagStyle) {
            append("  ${ movie.media_type?.toUpperCase(Locale.ROOT)}  ")
        }
        append(divider)
        append(movie.date)
        append(divider)
        append(movie.rating)
        append(divider)
        movie.original_language?.language()?.let { append(it) }



    }
    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        Text(
            text = text,
            style = typography.body2,
            modifier = modifier
        )

    }
}

fun String.language(): String {
    val loc = Locale(this)
    return loc.getDisplayLanguage(loc)
}



@Composable
fun ColumnSpacer(value: Int) = Spacer(modifier = Modifier.height(value.dp))

