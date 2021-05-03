package com.example.composemovieapp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.composemovieapp.presentation.ui.movie_list.MovieGenre


@ExperimentalComposeUiApi
@Composable
fun SearchAppBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
    categories: List<MovieGenre>,
    selectedCategory: MovieGenre?,
    onSelectedCategoryChanged: (String) -> Unit,
    onToggleTheme: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = MaterialTheme.colors.secondary,
        elevation = 8.dp,
    ) {
        Column {
            Row(modifier = Modifier.fillMaxWidth()) {

                val keyboardController = LocalSoftwareKeyboardController.current
                TextField(
                    modifier =Modifier.fillMaxWidth(.9f)
                        .padding(8.dp),
                    value = query,
                    onValueChange = {onQueryChanged(it)},
                    label = { Text(text = "Search") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions( onDone = {
                        onExecuteSearch()
                        keyboardController?.hideSoftwareKeyboard()
                    }),
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search Icon") },

                    textStyle = TextStyle(color = MaterialTheme.colors.onSurface),

                )
                BoxWithConstraints(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    contentAlignment = Alignment.CenterEnd,
                ) {

                    IconButton(
                        modifier = Modifier.padding(8.dp),
                        onClick = onToggleTheme,
                    ) {
                        Icon(Icons.Filled.LightMode, contentDescription = "Toggle Dark/Light Theme")
                    }

                }


            }
            val scrollState = rememberLazyListState()
            LazyRow(
                modifier = Modifier
                    .padding(start = 8.dp, bottom = 8.dp),
                state = scrollState,
            ) {
                items(categories){
                    MovieCategoryChip(
                        category = it.value,
                        isSelected = selectedCategory == it,
                        onSelectedCategoryChanged = {
                            onSelectedCategoryChanged(it)
                        },
                        onExecuteSearch = {
                            onExecuteSearch()
                        },
                    )

                }
            }
        }
    }
}