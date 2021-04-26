import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.composemovieapp.presentation.components.ShimmerRecipeCardItem

@ExperimentalFoundationApi
@Composable
fun LoadingMovieListShimmer(
    imageHeight: Dp,
    padding: Dp = 8.dp
) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val cardWidthPx = with(LocalDensity.current) { (maxWidth - (padding*2)).toPx() }
        val cardHeightPx = with(LocalDensity.current) { (imageHeight - padding).toPx() }
        val gradientWidth: Float = (0.2f * cardHeightPx)

        val infiniteTransition = rememberInfiniteTransition()
        val xCardShimmer = infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = (cardWidthPx + gradientWidth),
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1300,
                    easing = LinearEasing,
                    delayMillis = 300
                ),
                repeatMode = RepeatMode.Restart
            )
        )
        val yCardShimmer = infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = (cardHeightPx + gradientWidth),
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1300,
                    easing = LinearEasing,
                    delayMillis = 300
                ),
                repeatMode = RepeatMode.Restart
            )
        )

        val colors = listOf(
            Color.LightGray.copy(alpha = .9f),
            Color.LightGray.copy(alpha = .3f),
            Color.LightGray.copy(alpha = .9f),
        )

        LazyVerticalGrid(
            cells = GridCells.Fixed(2)
        ) {
            items(5) {
                Box(
                    modifier = Modifier.fillMaxWidth(0.5f)
                        .padding(8.dp)
                ) {
                ShimmerRecipeCardItem(
                    colors = colors,
                    xShimmer = xCardShimmer.value,
                    yShimmer = yCardShimmer.value,
                    cardHeight = imageHeight,
                    gradientWidth = gradientWidth,
                    padding = padding
                )
            }
            }
        }
    }


}