package com.rickandmorty.core.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rickandmorty.R
import com.rickandmorty.presentation.main.OrientationState

private val PORTRAIT_ERROR_IMG_SIZE = 64.dp
private val LANDSCAPE_ERROR_IMG_SIZE = 32.dp

@Composable
fun ErrorPagingItem(modifier: Modifier) {
    val locationHeight by remember {
        mutableStateOf(
            if (OrientationState.orientation.value == Configuration.ORIENTATION_PORTRAIT) {
                PORTRAIT_HEIGHT
            } else {
                LANDSCAPE_HEIGHT
            }
        )
    }

    val locationWidth by remember {
        mutableStateOf(
            if (OrientationState.orientation.value == Configuration.ORIENTATION_PORTRAIT) {
                PORTRAIT_WIDTH
            } else {
                LANDSCAPE_WIDTH
            }
        )
    }

    val errorImgSize by remember {
        mutableStateOf(
            if (OrientationState.orientation.value == Configuration.ORIENTATION_PORTRAIT) {
                PORTRAIT_ERROR_IMG_SIZE
            } else {
                LANDSCAPE_ERROR_IMG_SIZE
            }
        )
    }

    ElevatedCard(
        shape = RoundedCornerShape(10)
    ) {
        Row(
            modifier = modifier.size(
                width = locationWidth,
                height = locationHeight
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ErrorItemImage(
                modifier = modifier
                    .padding(horizontal = 2.dp)
                    .size(errorImgSize)
            )
            ErrorItemMessage(
                modifier = modifier
            )
        }
    }
}

@Composable
private fun ErrorItemMessage(modifier: Modifier) {
    Text(
        modifier = modifier,
        text = "Something happened \uD83D\uDE25",
        textAlign = TextAlign.Center,
        style = if (OrientationState.orientation.value == Configuration.ORIENTATION_PORTRAIT) {
            MaterialTheme.typography.bodySmall
        } else {
            MaterialTheme.typography.displaySmall
        }
    )
}

@Composable
private fun ErrorItemImage(modifier: Modifier) {
    Image(
        modifier = modifier,
        painter = painterResource(id = R.drawable.error_sign),
        contentDescription = "location load error",
        contentScale = ContentScale.Crop
    )
}