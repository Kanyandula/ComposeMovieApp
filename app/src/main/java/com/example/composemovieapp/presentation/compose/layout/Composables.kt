package com.example.composemovieapp.presentation.compose.layout
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.composemovieapp.domain.model.Movie
import com.example.composemovieapp.util.genreToCommaSeparatedString
import java.util.*

@Composable
fun MovieInfo(movie: Movie) {
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
    Text(
        text = "Genre",
        style = typography.h5,
        fontWeight = FontWeight.Bold,
        maxLines = 4,
        modifier = padding
    )

    ColumnSpacer(8)
    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        movie.genres?.genreToCommaSeparatedString().let {
            if (it != null) {
                Text(
                    text = it,
                    style = typography.body2,
                    maxLines = 4,
                    modifier = padding
                )
            }
        }
    }
    Spacer(Modifier.height(16.dp))
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
            append("  ${movie.original_language?.language()}  ")
        }
        append(divider)
        append(movie.date)
        append(divider)
        append(movie.rating)
        append(divider)
        movie.media_type?.let { append(it.toUpperCase(Locale.ROOT)) }


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
