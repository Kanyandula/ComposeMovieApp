package com.example.composemovieapp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.WarningAmber
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NothingHere(){
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Column(modifier = Modifier.align(Alignment.Center)){


            Icon(
                Icons.Rounded.WarningAmber,
                "back button",
                tint = Color.Red,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
                    .size(100.dp),
            )

            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                text = "There's nothing here",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h4
            )
        }

    }
}


