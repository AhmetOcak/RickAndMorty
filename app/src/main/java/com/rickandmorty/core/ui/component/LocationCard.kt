package com.rickandmorty.core.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.rickandmorty.presentation.main.OrientationState

val PORTRAIT_HEIGHT = 96.dp
val LANDSCAPE_HEIGHT = 48.dp

val PORTRAIT_WIDTH = 256.dp
val LANDSCAPE_WIDTH = 128.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Location(
    modifier: Modifier,
    contentImage: Int,
    locationName: String,
    locationId: Int,
    residents: ArrayList<String>,
    selectedLocationId: Int,
    onClick: (Int, ArrayList<String>) -> Unit
) {
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

    ElevatedCard(
        shape = RoundedCornerShape(10),
        onClick = {
            // We prevent the callback function from calling characters when a selected location is clicked a second time.
            if (locationId != selectedLocationId) {
                onClick(locationId, residents)
            }
        },
        // If the location is not selected, the card color is made gray.
        colors = CardDefaults.elevatedCardColors(
            containerColor = if (selectedLocationId != locationId)
                if (isSystemInDarkTheme()) Color.Gray else Color.LightGray
            else
                MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = modifier.size(
                width = locationWidth,
                height = locationHeight
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (OrientationState.orientation.value == Configuration.ORIENTATION_PORTRAIT) {
                LocationImage(
                    modifier = modifier
                        .weight(1f)
                        .fillMaxSize(),
                    contentImage = contentImage,
                    isSelected = selectedLocationId == locationId
                )
            }
            LocationName(
                modifier = modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp),
                locationName = locationName
            )
        }
    }
}

@Composable
private fun LocationName(
    modifier: Modifier,
    locationName: String
) {
    Text(
        modifier = modifier,
        text = locationName,
        style = MaterialTheme.typography.bodySmall,
        textAlign = TextAlign.Center,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun LocationImage(
    modifier: Modifier,
    contentImage: Int,
    isSelected: Boolean
) {
    Image(
        modifier = modifier,
        painter = painterResource(id = contentImage),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        // If the location is not selected, the image is displayed in grayscale.
        colorFilter = if (!isSelected) ColorFilter.colorMatrix(
            ColorMatrix().apply {
                setToSaturation(0f)
            }
        ) else null
    )
}